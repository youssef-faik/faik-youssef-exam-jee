package com.example.faikyoussef.service.impl;

import com.example.faikyoussef.dto.ClientDto;
import com.example.faikyoussef.entity.Client;
import com.example.faikyoussef.exception.ResourceNotFoundException;
import com.example.faikyoussef.mapper.ClientMapper;
import com.example.faikyoussef.repository.ClientRepository;
import com.example.faikyoussef.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientDto saveClient(ClientDto clientDto) {
        Client client = ClientMapper.toEntity(clientDto);
        Client savedClient = clientRepository.save(client);
        return ClientMapper.toDto(savedClient);
    }

    @Override
    public ClientDto getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        return ClientMapper.toDto(client);
    }

    @Override
    public List<ClientDto> getAllClients() {
        return clientRepository.findAll().stream()
                .map(ClientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDto updateClient(Long id, ClientDto clientDto) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));

        existingClient.setName(clientDto.getName());
        existingClient.setEmail(clientDto.getEmail());
        // Update other fields as necessary

        Client updatedClient = clientRepository.save(existingClient);
        return ClientMapper.toDto(updatedClient);
    }

    @Override
    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client not found with id: " + id);
        }
        clientRepository.deleteById(id);
    }
}
