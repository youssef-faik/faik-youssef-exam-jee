package com.example.faikyoussef.controller;

import com.example.faikyoussef.dto.RemboursementDto;
import com.example.faikyoussef.service.RemboursementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/remboursements")
@Tag(name = "Remboursement Management", description = "APIs for managing remboursements")
public class RemboursementController {

    private final RemboursementService remboursementService;

    public RemboursementController(RemboursementService remboursementService) {
        this.remboursementService = remboursementService;
    }

    @PostMapping("/credits/{creditId}")
    @Operation(summary = "Create a new remboursement for a credit",
            description = "Creates a new remboursement for a specific credit with the provided details.",            requestBody = @RequestBody(description = "Remboursement data to create",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RemboursementDto.class),
                            examples = @ExampleObject(value = "{\"amount\": 500.0, \"date\": \"2025-06-01\", \"type\": \"PAYMENT\"}"))),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Remboursement created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Credit not found")
            })
    public ResponseEntity<RemboursementDto> createRemboursement(@PathVariable Long creditId, @Valid @org.springframework.web.bind.annotation.RequestBody RemboursementDto remboursementDto) {
        RemboursementDto savedRemboursement = remboursementService.saveRemboursement(remboursementDto, creditId);
        return new ResponseEntity<>(savedRemboursement, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a remboursement by ID",
            description = "Retrieves a specific remboursement by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Remboursement found"),
                    @ApiResponse(responseCode = "404", description = "Remboursement not found")
            })
    public ResponseEntity<RemboursementDto> getRemboursementById(@PathVariable Long id) {
        RemboursementDto remboursementDto = remboursementService.getRemboursementById(id);
        return ResponseEntity.ok(remboursementDto);
    }

    @GetMapping("/credits/{creditId}")
    @Operation(summary = "Get all remboursements for a specific credit",
            description = "Retrieves a list of all remboursements associated with a specific credit ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
                    @ApiResponse(responseCode = "404", description = "Credit not found")
            })
    public ResponseEntity<List<RemboursementDto>> getAllRemboursementsForCredit(@PathVariable Long creditId) {
        List<RemboursementDto> remboursements = remboursementService.getAllRemboursementsForCredit(creditId);
        return ResponseEntity.ok(remboursements);
    }

    @GetMapping
    @Operation(summary = "Get all remboursements",
            description = "Retrieves a list of all remboursements.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
            })
    public ResponseEntity<List<RemboursementDto>> getAllRemboursements() {
        List<RemboursementDto> remboursements = remboursementService.getAllRemboursements();
        return ResponseEntity.ok(remboursements);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing remboursement",
            description = "Updates an existing remboursement with the provided details. Note: Credit ID cannot be changed via this endpoint.",            requestBody = @RequestBody(description = "Remboursement data to update",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RemboursementDto.class),
                            examples = @ExampleObject(value = "{\"amount\": 550.0, \"date\": \"2025-06-15\", \"type\": \"PAYMENT\"}"))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Remboursement updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Remboursement not found")
            })
    public ResponseEntity<RemboursementDto> updateRemboursement(@PathVariable Long id, @Valid @org.springframework.web.bind.annotation.RequestBody RemboursementDto remboursementDto) {
        RemboursementDto updatedRemboursement = remboursementService.updateRemboursement(id, remboursementDto);
        return ResponseEntity.ok(updatedRemboursement);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a remboursement by ID",
            description = "Deletes a specific remboursement by its ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Remboursement deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Remboursement not found")
            })
    public ResponseEntity<Void> deleteRemboursement(@PathVariable Long id) {
        remboursementService.deleteRemboursement(id);
        return ResponseEntity.noContent().build();
    }
}
