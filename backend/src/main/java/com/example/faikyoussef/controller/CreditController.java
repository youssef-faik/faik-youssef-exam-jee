package com.example.faikyoussef.controller;

import com.example.faikyoussef.dto.CreditDto;
import com.example.faikyoussef.service.CreditService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/credits")
public class CreditController {

    private final CreditService creditService;

    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }

    @PostMapping("/clients/{clientId}")
    public ResponseEntity<CreditDto> createCredit(@PathVariable Long clientId, @Valid @RequestBody CreditDto creditDto) {
        CreditDto savedCredit = creditService.saveCredit(creditDto, clientId);
        return new ResponseEntity<>(savedCredit, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditDto> getCreditById(@PathVariable Long id) {
        CreditDto creditDto = creditService.getCreditById(id);
        return ResponseEntity.ok(creditDto);
    }

    @GetMapping
    public ResponseEntity<List<CreditDto>> getAllCredits(@RequestParam(required = false) String status) {
        List<CreditDto> credits;
        if (status != null && !status.isEmpty()) {
            credits = creditService.getCreditsByStatus(status);
        } else {
            credits = creditService.getAllCredits();
        }
        return ResponseEntity.ok(credits);
    }

    @GetMapping("/clients/{clientId}")
    public ResponseEntity<List<CreditDto>> getCreditsByClientId(@PathVariable Long clientId) {
        List<CreditDto> credits = creditService.getCreditsByClientId(clientId);
        return ResponseEntity.ok(credits);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreditDto> updateCredit(@PathVariable Long id, @Valid @RequestBody CreditDto creditDto) {
        CreditDto updatedCredit = creditService.updateCredit(id, creditDto);
        return ResponseEntity.ok(updatedCredit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCredit(@PathVariable Long id) {
        creditService.deleteCredit(id);
        return ResponseEntity.noContent().build();
    }
}
