package com.estudiomusical.backend.service;

import com.estudiomusical.backend.model.Reservation;
import com.estudiomusical.backend.model.Room;
import com.estudiomusical.backend.repository.ReservationRepository;
import com.estudiomusical.backend.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(String id) {
        return reservationRepository.findById(id);
    }

    public List<Reservation> getReservationsByCustomer(String email) {
        return reservationRepository.findByCustomerEmail(email);
    }

    public List<Reservation> getReservationsByRoom(String roomId) {
        return reservationRepository.findByRoomId(roomId);
    }

    public boolean isRoomAvailable(String roomId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Reservation> overlappingReservations = reservationRepository
                .findByRoomIdAndStartTimeBetween(roomId, startTime.minusHours(24), endTime.plusHours(24));
        
        return overlappingReservations.stream()
                .noneMatch(reservation -> 
                    !reservation.getPaymentStatus().equals("cancelled") &&
                    ((startTime.isBefore(reservation.getEndTime()) && endTime.isAfter(reservation.getStartTime())))
                );
    }

    public Double calculateEstimatedCost(String roomId, LocalDateTime startTime, LocalDateTime endTime) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + roomId));
        
        long hours = Duration.between(startTime, endTime).toHours();
        if (hours == 0) {
            hours = 1; // Minimum 1 hour
        }
        
        return room.getPricePerHour() * hours;
    }

    public Reservation createReservation(Reservation reservation) {
        // Validate room availability
        if (!isRoomAvailable(reservation.getRoomId(), reservation.getStartTime(), reservation.getEndTime())) {
            throw new RuntimeException("Room is not available for the selected time slot");
        }
        
        // Calculate estimated cost
        Double estimatedCost = calculateEstimatedCost(
                reservation.getRoomId(), 
                reservation.getStartTime(), 
                reservation.getEndTime()
        );
        reservation.setEstimatedCost(estimatedCost);
        
        // Set default values
        reservation.setPaymentStatus("pending");
        reservation.setCreatedAt(LocalDateTime.now());
        reservation.setUpdatedAt(LocalDateTime.now());
        
        return reservationRepository.save(reservation);
    }

    public Reservation updateReservationPaymentStatus(String id, String paymentStatus) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));
        
        reservation.setPaymentStatus(paymentStatus);
        reservation.setUpdatedAt(LocalDateTime.now());
        
        return reservationRepository.save(reservation);
    }

    public Reservation updateReservation(String id, Reservation reservationDetails) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));
        
        // Check if time slot is available if time is being changed
        if (!reservation.getStartTime().equals(reservationDetails.getStartTime()) ||
            !reservation.getEndTime().equals(reservationDetails.getEndTime())) {
            if (!isRoomAvailable(reservationDetails.getRoomId(), 
                    reservationDetails.getStartTime(), 
                    reservationDetails.getEndTime())) {
                throw new RuntimeException("Room is not available for the selected time slot");
            }
        }
        
        // Recalculate cost if time changed
        Double estimatedCost = calculateEstimatedCost(
                reservationDetails.getRoomId(), 
                reservationDetails.getStartTime(), 
                reservationDetails.getEndTime()
        );
        
        reservation.setRoomId(reservationDetails.getRoomId());
        reservation.setCustomerName(reservationDetails.getCustomerName());
        reservation.setCustomerEmail(reservationDetails.getCustomerEmail());
        reservation.setCustomerPhone(reservationDetails.getCustomerPhone());
        reservation.setStartTime(reservationDetails.getStartTime());
        reservation.setEndTime(reservationDetails.getEndTime());
        reservation.setRequestedItems(reservationDetails.getRequestedItems());
        reservation.setEstimatedCost(estimatedCost);
        reservation.setUpdatedAt(LocalDateTime.now());
        
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(String id) {
        reservationRepository.deleteById(id);
    }

}
