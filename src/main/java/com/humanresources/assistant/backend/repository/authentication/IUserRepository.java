package com.humanresources.assistant.backend.repository.authentication;

import com.humanresources.assistant.backend.model.authentication.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String userName);

    Boolean existsByEmail(String emailAddress);

}
