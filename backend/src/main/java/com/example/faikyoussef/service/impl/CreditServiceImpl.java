package com.example.faikyoussef.service.impl;

import com.example.faikyoussef.dto.CreditDto;
import com.example.faikyoussef.entity.Client;
import com.example.faikyoussef.entity.Credit;
import com.example.faikyoussef.exception.ResourceNotFoundException;
import com.example.faikyoussef.mapper.CreditMapper;
import com.example.faikyoussef.repository.ClientRepository;
import com.example.faikyoussef.repository.CreditRepository;
import com.example.faikyoussef.service.CreditService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CreditServiceImpl implements CreditService {

    private final CreditRepository creditRepository;
    private final ClientRepository clientRepository;

    public CreditServiceImpl(CreditRepository creditRepository, ClientRepository clientRepository) {
        this.creditRepository = creditRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public CreditDto saveCredit(CreditDto creditDto, Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + clientId));
        Credit credit = CreditMapper.toEntity(creditDto);
        credit.setClient(client);

        if (credit.getStatus() == null || credit.getStatus().trim().isEmpty()) {
            credit.setStatus("ACTIVE"); // Default status
        }

        Credit savedCredit = creditRepository.save(credit);
        return CreditMapper.toDto(savedCredit);
    }

    @Override
    public CreditDto getCreditById(Long id) {
        Credit credit = creditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Credit not found with id: " + id));
        return CreditMapper.toDto(credit);
    }

    @Override
    public List<CreditDto> getAllCredits() {
        return creditRepository.findAll().stream()
                .map(CreditMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CreditDto> getCreditsByClientId(Long clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new ResourceNotFoundException("Client not found with id: " + clientId);
        }
        return creditRepository.findByClientId(clientId).stream()
                .map(CreditMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CreditDto> getCreditsByStatus(String status) {
        return creditRepository.findByStatus(status).stream()
                .map(CreditMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CreditDto updateCredit(Long id, CreditDto creditDto) {
        Credit existingCredit = creditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Credit not found with id: " + id));

        // Map DTO to an entity, potentially changing its type based on DTO content
        Credit creditToUpdate = CreditMapper.toEntity(creditDto);

        // Preserve ID and existing client relationship
        creditToUpdate.setId(existingCredit.getId());
        creditToUpdate.setClient(existingCredit.getClient()); 

        // Ensure status from DTO is applied, or keep existing if DTO's status is null
        if (creditDto.getStatus() == null && existingCredit.getStatus() != null) {
            creditToUpdate.setStatus(existingCredit.getStatus());
        }
        // If DTO status is explicitly set (even to empty), it will be used by mapper or here.
        // If status is critical and should not be easily nulled by DTO, add specific logic.

        Credit updatedCredit = creditRepository.save(creditToUpdate);
        return CreditMapper.toDto(updatedCredit);
    }

    @Override
    public void deleteCredit(Long id) {
        if (!creditRepository.existsById(id)) {
            throw new ResourceNotFoundException("Credit not found with id: " + id);
        }
        creditRepository.deleteById(id);
    }
}
