package com.example.faikyoussef.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Client Data Transfer Object - Represents client information")
public class ClientDto {
    @Schema(description = "Unique identifier of the client", example = "1")
    private Long id;
    
    @Schema(description = "Full name of the client", example = "John Doe", required = true)
    private String name;
    
    @Schema(description = "Email address of the client", example = "john.doe@example.com", required = true)
    private String email;
    // Assuming you don't want to expose Credits directly in ClientDto to avoid circular dependencies or overly large objects.
    // If you need them, you can add List<CreditDto> credits; and handle mapping.
}
