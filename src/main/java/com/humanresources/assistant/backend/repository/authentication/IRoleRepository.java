package com.humanresources.assistant.backend.repository.authentication;

import com.humanresources.assistant.backend.enums.ERole;
import com.humanresources.assistant.backend.model.authentication.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);
}
