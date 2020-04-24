package com.humanresources.assistant.backend.converters;

import com.humanresources.assistant.backend.dto.ClientDto;
import com.humanresources.assistant.backend.entity.Client;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ClientConverters extends Generic<Client, ClientDto> {

    public Function<List<Client>, List<ClientDto>> convertClientEntityListToDto = convertListEntitiesToDto();
    public Function<List<ClientDto>, List<Client>> convertClientDtoListToEntity = convertListDtoToEntities();
    public Function<Client, ClientDto> convertClientEntityToDto = convertEntityToDto();
    public Function<ClientDto, Client> convertClientDtoToEntity = convertDtoToEntity();

    @Override
    protected Function<Client, ClientDto> convertEntityToDto() {
        return client -> ClientDto.builder()
            .id(client.getId())
            .countryName(client.getCountryName())
            .clientName(client.getClientName())
            .build();
    }

    @Override
    protected Function<ClientDto, Client> convertDtoToEntity() {
        return client -> Client.builder()
            .countryName(client.getCountryName())
            .clientName(client.getClientName())
            .build();
    }

    @Override
    protected Function<List<Client>, List<ClientDto>> convertListEntitiesToDto() {
        return clients -> clients.stream()
            .map(client -> convertEntityToDto().apply(client))
            .collect(Collectors.toList());
    }

    @Override
    protected Function<List<ClientDto>, List<Client>> convertListDtoToEntities() {
        return clients -> clients.stream()
            .map(client -> convertDtoToEntity().apply(client))
            .collect(Collectors.toList());
    }

}
