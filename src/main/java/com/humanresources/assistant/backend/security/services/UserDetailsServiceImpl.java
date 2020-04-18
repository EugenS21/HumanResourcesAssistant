package com.humanresources.assistant.backend.security.services;

import com.humanresources.assistant.backend.model.authentication.User;
import com.humanresources.assistant.backend.repository.authentication.IUserRepository;
import javax.transaction.Transactional;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    IUserRepository userRepository;

    @Override
    @Transactional
    @SneakyThrows
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s was not found", username)));
        return UserDetailsImpl.buid(user);
    }
}
