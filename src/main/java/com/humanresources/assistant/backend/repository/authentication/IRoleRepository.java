package com.humanresources.assistant.backend.repository.authentication;

import com.humanresources.assistant.backend.entity.authentication.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {

    @Query ("select u from Role u where u.name=?1")
    Optional<Role> findByNameLike(String name);
}
