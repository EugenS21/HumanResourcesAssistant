package com.humanresources.assistant.backend.service;

import static com.google.common.collect.Lists.newArrayList;

import com.humanresources.assistant.backend.converters.ClientConverters;
import com.humanresources.assistant.backend.dto.ClientDto;
import com.humanresources.assistant.backend.entity.Client;
import com.humanresources.assistant.backend.exceptions.ClientNotFound;
import com.humanresources.assistant.backend.repository.ClientRepository;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ClientConverters clientConverters;

    public List<ClientDto> getAllClients() {
        return
            clientConverters.convertClientEntityListToDto.apply(newArrayList(clientRepository.findAll()));
    }

    public void saveClient(ClientDto client) {
        clientRepository.save(clientConverters.convertClientDtoToEntity.apply(client));
    }

    @SneakyThrows
    public ClientDto updateClient(Integer id, ClientDto clientDto) {
        final Client foundClient = clientRepository.findById(id).orElseThrow(ClientNotFound::new);
        foundClient.setClientName(clientDto.getClientName());
        foundClient.setCountryName(clientDto.getCountryName());
        clientRepository.save(foundClient);
        return clientConverters.convertClientEntityToDto.apply(foundClient);
    }

    public void deleteClient(Integer id) {
        clientRepository.deleteById(id);
    }
}
