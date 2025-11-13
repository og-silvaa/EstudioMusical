package com.estudiomusical.backend.service;

import com.estudiomusical.backend.model.Room;
import com.estudiomusical.backend.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public List<Room> getAvailableRooms() {
        return roomRepository.findByAvailable(true);
    }

    public Optional<Room> getRoomById(String id) {
        return roomRepository.findById(id);
    }

    public Room createRoom(Room room) {
        room.setAvailable(true);
        return roomRepository.save(room);
    }

    public Room updateRoom(String id, Room roomDetails) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));
        
        room.setName(roomDetails.getName());
        room.setDescription(roomDetails.getDescription());
        room.setCapacity(roomDetails.getCapacity());
        room.setPricePerHour(roomDetails.getPricePerHour());
        room.setAvailableInstruments(roomDetails.getAvailableInstruments());
        room.setAvailableMicrophones(roomDetails.getAvailableMicrophones());
        room.setAvailable(roomDetails.getAvailable());
        
        return roomRepository.save(room);
    }

    public void deleteRoom(String id) {
        roomRepository.deleteById(id);
    }

}
