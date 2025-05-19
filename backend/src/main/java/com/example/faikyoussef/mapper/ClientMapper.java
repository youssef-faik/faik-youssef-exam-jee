package com.example.faikyoussef.mapper;

import com.example.faikyoussef.dto.ClientDto;
import com.example.faikyoussef.entity.Client;

public class ClientMapper {

    public static ClientDto toDto(Client client) {
        if (client == null) {
            return null;
        }
        ClientDto clientDto = new ClientDto();
        clientDto.setId(client.getId());
        clientDto.setName(client.getName());
        clientDto.setEmail(client.getEmail());
        // Credits are not mapped here to avoid circular dependencies.
        return clientDto;
    }

    public static Client toEntity(ClientDto clientDto) {
        if (clientDto == null) {
            return null;
        }
        Client client = new Client();
        client.setId(clientDto.getId());
        client.setName(clientDto.getName());
        client.setEmail(clientDto.getEmail());
        // Credits are not mapped here.
        return client;
    }
}
