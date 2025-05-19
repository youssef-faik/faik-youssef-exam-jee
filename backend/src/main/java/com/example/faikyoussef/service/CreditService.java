package com.example.faikyoussef.service;

import com.example.faikyoussef.dto.CreditDto;
import java.util.List;

public interface CreditService {
    CreditDto saveCredit(CreditDto creditDto, Long clientId);
    CreditDto getCreditById(Long id);
    List<CreditDto> getAllCredits();
    List<CreditDto> getCreditsByClientId(Long clientId);
    CreditDto updateCredit(Long id, CreditDto creditDto);
    void deleteCredit(Long id);
    // Add methods for specific credit types or business logic as needed
}
