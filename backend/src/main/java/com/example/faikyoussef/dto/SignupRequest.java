package com.example.faikyoussef.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Schema(description = "Registration Request - New user registration information")
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    @Schema(description = "Username", example = "johndoe", required = true)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    @Schema(description = "Email address", example = "john.doe@example.com", required = true)
    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    @Schema(description = "Password", example = "password123", required = true)
    private String password;

    @Schema(description = "Set of roles for the user", example = "[\"ROLE_CLIENT\"]")
    private Set<String> roles;
    
    @Schema(description = "Client information if registering as a client", required = false)
    private ClientDto clientInfo;
}
