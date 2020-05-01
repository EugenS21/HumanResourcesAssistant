package com.humanresources.assistant.backend.repository.authentication;

import com.humanresources.assistant.backend.entity.authentication.RoleEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {

    @Query ("select u from RoleEntity u where u.name=?1")
    Optional<RoleEntity> findByNameLike(String name);
}
