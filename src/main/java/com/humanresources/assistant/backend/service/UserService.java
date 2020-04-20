package com.humanresources.assistant.backend.service;

import com.humanresources.assistant.backend.entity.authentication.User;
import com.humanresources.assistant.backend.repository.authentication.IUserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    IUserRepository iUserRepository;

    public List<User> getAllRegisteredUsers() {
        return iUserRepository.findAll();
    }
}