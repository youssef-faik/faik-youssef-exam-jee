package com.example.faikyoussef.service.impl;

import com.example.faikyoussef.dto.RemboursementDto;
import com.example.faikyoussef.entity.Credit;
import com.example.faikyoussef.entity.Remboursement;
import com.example.faikyoussef.exception.ResourceNotFoundException;
import com.example.faikyoussef.mapper.RemboursementMapper;
import com.example.faikyoussef.repository.CreditRepository;
import com.example.faikyoussef.repository.RemboursementRepository;
import com.example.faikyoussef.service.RemboursementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RemboursementServiceImpl implements RemboursementService {

    private final RemboursementRepository remboursementRepository;
    private final CreditRepository creditRepository; // To link remboursement to a credit

    public RemboursementServiceImpl(RemboursementRepository remboursementRepository, CreditRepository creditRepository) {
        this.remboursementRepository = remboursementRepository;
        this.creditRepository = creditRepository;
    }

    @Override
    public RemboursementDto saveRemboursement(RemboursementDto remboursementDto, Long creditId) {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new ResourceNotFoundException("Credit not found with id: " + creditId));

        if (remboursementDto.getAmount() <= 0) {
            throw new IllegalArgumentException("Remboursement amount must be positive.");
        }

        if (remboursementDto.getAmount() > credit.getRemainingBalance()) {
            throw new IllegalArgumentException("Remboursement amount cannot exceed the remaining balance of the credit. Remaining balance: " + credit.getRemainingBalance());
        }

        Remboursement remboursement = RemboursementMapper.toEntity(remboursementDto);
        remboursement.setCredit(credit);
        Remboursement savedRemboursement = remboursementRepository.save(remboursement);

        // Refresh credit to get updated list of remboursements for accurate remaining balance
        Credit updatedCredit = creditRepository.findById(creditId)
                .orElseThrow(() -> new ResourceNotFoundException("Credit not found with id: " + creditId)); // Should not happen

        if (updatedCredit.getRemainingBalance() <= 0) {
            updatedCredit.setStatus("COMPLETED");
            creditRepository.save(updatedCredit);
        }

        return RemboursementMapper.toDto(savedRemboursement);
    }

    @Override
    public RemboursementDto getRemboursementById(Long id) {
        Remboursement remboursement = remboursementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Remboursement not found with id: " + id));
        return RemboursementMapper.toDto(remboursement);
    }

    @Override
    public List<RemboursementDto> getAllRemboursementsForCredit(Long creditId) {
        if (!creditRepository.existsById(creditId)) {
            throw new ResourceNotFoundException("Credit not found with id: " + creditId);
        }
        // Assuming findByCreditId exists in RemboursementRepository
        return remboursementRepository.findByCreditId(creditId).stream()
                .map(RemboursementMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RemboursementDto> getAllRemboursements() {
        return remboursementRepository.findAll().stream()
                .map(RemboursementMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RemboursementDto updateRemboursement(Long id, RemboursementDto remboursementDto) {
        Remboursement existingRemboursement = remboursementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Remboursement not found with id: " + id));

        existingRemboursement.setDate(remboursementDto.getDate());
        existingRemboursement.setAmount(remboursementDto.getAmount());
        existingRemboursement.setType(remboursementDto.getType());
        // Potentially update credit if remboursementDto.getCreditId() is different and valid

        Remboursement updatedRemboursement = remboursementRepository.save(existingRemboursement);
        return RemboursementMapper.toDto(updatedRemboursement);
    }

    @Override
    public void deleteRemboursement(Long id) {
        if (!remboursementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Remboursement not found with id: " + id);
        }
        remboursementRepository.deleteById(id);
    }
}
