package com.example.faikyoussef.service;

import com.example.faikyoussef.dto.RemboursementDto;
import java.util.List;

public interface RemboursementService {
    RemboursementDto saveRemboursement(RemboursementDto remboursementDto, Long creditId);
    RemboursementDto getRemboursementById(Long id);
    List<RemboursementDto> getAllRemboursementsForCredit(Long creditId);
    List<RemboursementDto> getAllRemboursements();
    RemboursementDto updateRemboursement(Long id, RemboursementDto remboursementDto);
    void deleteRemboursement(Long id);
}
