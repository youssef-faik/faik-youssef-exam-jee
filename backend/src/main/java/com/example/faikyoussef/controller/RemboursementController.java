package com.example.faikyoussef.controller;

import com.example.faikyoussef.dto.RemboursementDto;
import com.example.faikyoussef.service.RemboursementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/remboursements")
public class RemboursementController {

    private final RemboursementService remboursementService;

    public RemboursementController(RemboursementService remboursementService) {
        this.remboursementService = remboursementService;
    }

    @PostMapping("/credits/{creditId}")
    public ResponseEntity<RemboursementDto> createRemboursement(@PathVariable Long creditId, @Valid @RequestBody RemboursementDto remboursementDto) {
        RemboursementDto savedRemboursement = remboursementService.saveRemboursement(remboursementDto, creditId);
        return new ResponseEntity<>(savedRemboursement, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RemboursementDto> getRemboursementById(@PathVariable Long id) {
        RemboursementDto remboursementDto = remboursementService.getRemboursementById(id);
        return ResponseEntity.ok(remboursementDto);
    }

    @GetMapping("/credits/{creditId}")
    public ResponseEntity<List<RemboursementDto>> getAllRemboursementsForCredit(@PathVariable Long creditId) {
        List<RemboursementDto> remboursements = remboursementService.getAllRemboursementsForCredit(creditId);
        return ResponseEntity.ok(remboursements);
    }

    @GetMapping
    public ResponseEntity<List<RemboursementDto>> getAllRemboursements() {
        List<RemboursementDto> remboursements = remboursementService.getAllRemboursements();
        return ResponseEntity.ok(remboursements);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RemboursementDto> updateRemboursement(@PathVariable Long id, @Valid @RequestBody RemboursementDto remboursementDto) {
        RemboursementDto updatedRemboursement = remboursementService.updateRemboursement(id, remboursementDto);
        return ResponseEntity.ok(updatedRemboursement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRemboursement(@PathVariable Long id) {
        remboursementService.deleteRemboursement(id);
        return ResponseEntity.noContent().build();
    }
}
