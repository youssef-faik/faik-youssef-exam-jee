package com.example.faikyoussef.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Date;

@Data
@Schema(description = "Credit Data Transfer Object - Represents credit information")
public class CreditDto {
    @Schema(description = "Unique identifier of the credit", example = "1")
    private Long id;
    
    @Schema(description = "Amount of the credit", example = "10000.0", required = true)
    private double amount;
    
    @Schema(description = "Date when the credit was created", example = "2025-05-19")
    private Date date;
    
    @Schema(description = "Status of the credit", example = "PENDING", allowableValues = {"PENDING", "APPROVED", "REJECTED"})
    private String status;
    
    @Schema(description = "ID of the client associated with this credit", example = "1")
    private Long clientId; // To link back to the client

    // Fields for specific credit types
    @Schema(description = "Type of credit", example = "PERSONNEL", allowableValues = {"PERSONNEL", "IMMOBILIER", "PROFESSIONNEL"})
    private String typeCredit; // To distinguish between credit types (PERSONNEL, IMMOBILIER, PROFESSIONNEL)
    
    @Schema(description = "Reason for the credit (for personal and professional credits)", example = "Home renovation")
    private String motif; // For CreditPersonnel and CreditProfessionnel
    
    @Schema(description = "Type of property (for real estate credits)", example = "APARTMENT")
    private String bienType; // For CreditImmobilier (matches entity's bienType)
    
    @Schema(description = "Company name (for professional credits)", example = "ABC Corporation")
    private String raisonSociale; // For CreditProfessionnel

    // Assuming you don't want to expose Remboursements directly in CreditDto.
}
