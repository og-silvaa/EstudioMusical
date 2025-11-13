package com.estudiomusical.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "rooms")
public class Room {

    @Id
    private String id;
    
    private String name;
    private String description;
    private Integer capacity;
    private Double pricePerHour;
    private List<String> availableInstruments;
    private List<String> availableMicrophones;
    private Boolean available;

}
