package com.example.faikyoussef.controller;

import com.example.faikyoussef.dto.CreditDto;
import com.example.faikyoussef.service.CreditService;
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
@RequestMapping("/api/v1/credits")
@Tag(name = "Credit Management", description = "APIs for managing credits")
public class CreditController {

    private final CreditService creditService;

    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }

    @PostMapping("/clients/{clientId}")
    @Operation(summary = "Create a new credit for a client",
            description = "Creates a new credit for a specific client with the provided details.",            requestBody = @RequestBody(description = "Credit data to create",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreditDto.class),
                            examples = @ExampleObject(value = "{\"amount\": 10000.0, \"status\": \"PENDING\", \"typeCredit\": \"PERSONNEL\"}"))),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Credit created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Client not found")
            })
    public ResponseEntity<CreditDto> createCredit(@PathVariable Long clientId, @Valid @org.springframework.web.bind.annotation.RequestBody CreditDto creditDto) {
        CreditDto savedCredit = creditService.saveCredit(creditDto, clientId);
        return new ResponseEntity<>(savedCredit, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a credit by ID",
            description = "Retrieves a specific credit by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Credit found"),
                    @ApiResponse(responseCode = "404", description = "Credit not found")
            })
    public ResponseEntity<CreditDto> getCreditById(@PathVariable Long id) {
        CreditDto creditDto = creditService.getCreditById(id);
        return ResponseEntity.ok(creditDto);
    }

    @GetMapping
    @Operation(summary = "Get all credits or filter by status",
            description = "Retrieves a list of all credits, optionally filtered by status (e.g., EN_ATTENTE, APPROUVE, REJETE).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
            })
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
    @Operation(summary = "Get all credits for a specific client",
            description = "Retrieves a list of all credits associated with a specific client ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
                    @ApiResponse(responseCode = "404", description = "Client not found")
            })
    public ResponseEntity<List<CreditDto>> getCreditsByClientId(@PathVariable Long clientId) {
        List<CreditDto> credits = creditService.getCreditsByClientId(clientId);
        return ResponseEntity.ok(credits);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing credit",
            description = "Updates an existing credit with the provided details. Note: Client ID cannot be changed via this endpoint.",            requestBody = @RequestBody(description = "Credit data to update",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreditDto.class),
                            examples = @ExampleObject(value = "{\"amount\": 12000.0, \"status\": \"APPROVED\", \"typeCredit\": \"PERSONNEL\"}"))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Credit updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Credit not found")
            })
    public ResponseEntity<CreditDto> updateCredit(@PathVariable Long id, @Valid @org.springframework.web.bind.annotation.RequestBody CreditDto creditDto) {
        CreditDto updatedCredit = creditService.updateCredit(id, creditDto);
        return ResponseEntity.ok(updatedCredit);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a credit by ID",
            description = "Deletes a specific credit by its ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Credit deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Credit not found")
            })
    public ResponseEntity<Void> deleteCredit(@PathVariable Long id) {
        creditService.deleteCredit(id);
        return ResponseEntity.noContent().build();
    }
}
