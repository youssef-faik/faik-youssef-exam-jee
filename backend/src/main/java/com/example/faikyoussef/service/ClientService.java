package com.example.faikyoussef.service;

import com.example.faikyoussef.dto.ClientDto;
import java.util.List;

public interface ClientService {
    ClientDto saveClient(ClientDto clientDto);
    ClientDto getClientById(Long id);
    List<ClientDto> getAllClients();
    ClientDto updateClient(Long id, ClientDto clientDto);
    void deleteClient(Long id);
}
