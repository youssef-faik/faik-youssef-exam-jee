package com.example.faikyoussef.dto;

import lombok.Data;
import java.util.Date;

@Data
public class RemboursementDto {
    private Long id;
    private double amount;
    private Date date;
    private String type;
    private Long creditId; // To link back to the credit
}
