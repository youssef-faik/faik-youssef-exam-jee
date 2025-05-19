package com.example.faikyoussef.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Date;

@Data
@Schema(description = "Remboursement Data Transfer Object - Represents repayment information")
public class RemboursementDto {
    @Schema(description = "Unique identifier of the repayment", example = "1")
    private Long id;
    
    @Schema(description = "Amount of the repayment", example = "500.0", required = true)
    private double amount;
    
    @Schema(description = "Date of the repayment", example = "2025-06-01", required = true)
    private Date date;
    
    @Schema(description = "Type of repayment", example = "PAYMENT", allowableValues = {"PAYMENT", "EARLY_PAYMENT", "PARTIAL_PAYMENT"})
    private String type;
    
    @Schema(description = "ID of the credit associated with this repayment", example = "1")
    private Long creditId; // To link back to the credit
}
