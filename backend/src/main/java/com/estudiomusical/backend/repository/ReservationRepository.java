package com.estudiomusical.backend.repository;

import com.estudiomusical.backend.model.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {
    
    List<Reservation> findByRoomId(String roomId);
    
    List<Reservation> findByCustomerEmail(String customerEmail);
    
    List<Reservation> findByPaymentStatus(String paymentStatus);
    
    List<Reservation> findByRoomIdAndStartTimeBetween(String roomId, LocalDateTime start, LocalDateTime end);
    
}
