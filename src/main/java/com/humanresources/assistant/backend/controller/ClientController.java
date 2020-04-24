package com.humanresources.assistant.backend.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.humanresources.assistant.backend.dto.ClientDto;
import com.humanresources.assistant.backend.service.ClientService;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    private final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    ClientService clientService;

    @GetMapping (value = "/client", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize ("hasAnyAuthority('hr','admin')")
    public ResponseEntity<List<ClientDto>> getAllClients() {
        return ResponseEntity.ok()
            .body(clientService.getAllClients());
    }

    @PostMapping (value = "/client", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize ("hasAuthority('admin')")
    public ResponseEntity<ClientDto> postNewClient(@Valid @RequestBody ClientDto client) {
        try {
            clientService.saveClient(client);
            return ResponseEntity.ok().body(client);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(client);
        }
    }

    @PutMapping (value = "/client/{id}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize ("hasAuthority('admin')")
    public ResponseEntity<ClientDto> putClient(@PathVariable (name = "id") Integer id,
        @Valid @RequestBody ClientDto clientDto) {
        try {
            clientService.updateClient(id, clientDto);
            return ResponseEntity.ok().body(clientDto);
        } catch (Exception e) {
            logger.error("PUT request could not be executed {}", e);
            return ResponseEntity.badRequest().body(clientDto);
        }
    }

    @DeleteMapping (value = "/client/{id}")
    @PreAuthorize ("hasAuthority('admin')")
    public ResponseEntity<String> deleteClient(@PathVariable (name = "id") @Valid Integer id) {
        try {
            clientService.deleteClient(id);
            return ResponseEntity.ok().body("Client was removed");
        } catch (Exception e) {
            logger.info("DELETE request could not be executed {}", e);
            return ResponseEntity.badRequest().body("Could not remove");
        }
    }

}
