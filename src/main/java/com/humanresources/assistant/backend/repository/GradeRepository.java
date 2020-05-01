package com.humanresources.assistant.backend.repository;

import com.humanresources.assistant.backend.entity.GradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<GradeEntity, Integer> {

}
