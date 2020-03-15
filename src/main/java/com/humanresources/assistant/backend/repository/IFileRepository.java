package com.humanresources.assistant.backend.repository;

import com.humanresources.assistant.backend.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFileRepository extends JpaRepository<File, String> {

}
