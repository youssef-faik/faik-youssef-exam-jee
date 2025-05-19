package com.example.faikyoussef.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Message Response - Simple message response")
public class MessageResponse {
    @Schema(description = "Response message", example = "User registered successfully!")
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }
}
