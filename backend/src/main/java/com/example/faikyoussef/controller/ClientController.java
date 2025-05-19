package com.example.faikyoussef.controller;

import com.example.faikyoussef.dto.ClientDto;
import com.example.faikyoussef.service.ClientService;
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
@RequestMapping("/api/v1/clients")
@Tag(name = "Client Management", description = "APIs for managing clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    @Operation(summary = "Create a new client",
            description = "Creates a new client with the provided details.",            requestBody = @RequestBody(description = "Client data to create",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientDto.class),
                            examples = @ExampleObject(value = "{\"name\": \"John Doe\", \"email\": \"john.doe@example.com\"}"))),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Client created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            })
    public ResponseEntity<ClientDto> createClient(@Valid @RequestBody ClientDto clientDto) {
        ClientDto savedClient = clientService.saveClient(clientDto);
        return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a client by ID",
            description = "Retrieves a specific client by their ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client found"),
                    @ApiResponse(responseCode = "404", description = "Client not found")
            })
    public ResponseEntity<ClientDto> getClientById(@PathVariable Long id) {
        ClientDto clientDto = clientService.getClientById(id);
        return ResponseEntity.ok(clientDto);
    }

    @GetMapping
    @Operation(summary = "Get all clients",
            description = "Retrieves a list of all clients.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
            })
    public ResponseEntity<List<ClientDto>> getAllClients() {
        List<ClientDto> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing client",
            description = "Updates an existing client with the provided details.",            requestBody = @RequestBody(description = "Client data to update",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientDto.class),
                            examples = @ExampleObject(value = "{\"name\": \"Jane Doe\", \"email\": \"jane.doe@example.com\"}"))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Client not found")
            })
    public ResponseEntity<ClientDto> updateClient(@PathVariable Long id, @Valid @RequestBody ClientDto clientDto) {
        ClientDto updatedClient = clientService.updateClient(id, clientDto);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a client by ID",
            description = "Deletes a specific client by their ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Client deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Client not found")
            })
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
