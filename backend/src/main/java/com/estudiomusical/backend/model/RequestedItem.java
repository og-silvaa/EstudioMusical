package com.estudiomusical.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestedItem {

    private String name;
    private Integer quantity;
    private String type; // "instrument" or "microphone"

}
