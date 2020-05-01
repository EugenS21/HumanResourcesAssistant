package com.humanresources.assistant.backend.converters;

import com.humanresources.assistant.backend.dto.ClientDto;
import com.humanresources.assistant.backend.entity.ClientEntity;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ClientConverters extends Generic<ClientEntity, ClientDto> {

    public Function<List<ClientEntity>, List<ClientDto>> convertClientEntityListToDto = convertListEntitiesToDto();
    public Function<List<ClientDto>, List<ClientEntity>> convertClientDtoListToEntity = convertListDtoToEntities();
    public Function<ClientEntity, ClientDto> convertClientEntityToDto = convertEntityToDto();
    public Function<ClientDto, ClientEntity> convertClientDtoToEntity = convertDtoToEntity();

    @Override
    protected Function<ClientEntity, ClientDto> convertEntityToDto() {
        return client -> ClientDto.builder()
            .id(client.getId())
            .countryName(client.getCountryName())
            .clientName(client.getClientName())
            .build();
    }

    @Override
    protected Function<ClientDto, ClientEntity> convertDtoToEntity() {
        return client -> ClientEntity.builder()
            .id(client.getId())
            .countryName(client.getCountryName())
            .clientName(client.getClientName())
            .build();
    }

    @Override
    protected Function<List<ClientEntity>, List<ClientDto>> convertListEntitiesToDto() {
        return clients -> clients.stream()
            .map(client -> convertEntityToDto().apply(client))
            .collect(Collectors.toList());
    }

    @Override
    protected Function<List<ClientDto>, List<ClientEntity>> convertListDtoToEntities() {
        return clients -> clients.stream()
            .map(client -> convertDtoToEntity().apply(client))
            .collect(Collectors.toList());
    }

}
