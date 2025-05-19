package com.example.faikyoussef.dto;

import lombok.Data;
import java.util.Date;

@Data
public class CreditDto {
    private Long id;
    private double amount;
    private Date date;
    private String status;
    private Long clientId; // To link back to the client

    // Fields for specific credit types
    private String typeCredit; // To distinguish between credit types (PERSONNEL, IMMOBILIER, PROFESSIONNEL)
    private String motif; // For CreditPersonnel and CreditProfessionnel
    private String bienType; // For CreditImmobilier (matches entity's bienType)
    private String raisonSociale; // For CreditProfessionnel

    // Assuming you don't want to expose Remboursements directly in CreditDto.
}
