package com.example.faikyoussef.dto;

import lombok.Data;

@Data
public class ClientDto {
    private Long id;
    private String name;
    private String email;
    // Assuming you don't want to expose Credits directly in ClientDto to avoid circular dependencies or overly large objects.
    // If you need them, you can add List<CreditDto> credits; and handle mapping.
}
