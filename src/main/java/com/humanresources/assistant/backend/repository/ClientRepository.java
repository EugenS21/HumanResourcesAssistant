package com.humanresources.assistant.backend.repository;

import com.humanresources.assistant.backend.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Integer> {

}
