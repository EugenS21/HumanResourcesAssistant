package com.humanresources.assistant.backend.repository.authentication;

import com.humanresources.assistant.backend.entity.authentication.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    Boolean existsByUsername(String userName);

    Boolean existsByEmail(String emailAddress);

    List<UserEntity> findAll();

}
