package com.estudiomusical.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "reservations")
public class Reservation {

    @Id
    private String id;
    
    private String roomId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    private List<RequestedItem> requestedItems;
    
    private Double estimatedCost;
    private String paymentStatus; // "pending", "paid", "cancelled"
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
