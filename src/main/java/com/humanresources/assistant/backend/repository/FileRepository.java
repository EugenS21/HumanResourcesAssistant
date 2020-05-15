package com.humanresources.assistant.backend.repository;

import com.humanresources.assistant.backend.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {

}
