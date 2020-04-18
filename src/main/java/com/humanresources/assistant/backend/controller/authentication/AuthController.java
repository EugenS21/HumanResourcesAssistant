package com.humanresources.assistant.backend.controller.authentication;

import com.humanresources.assistant.backend.enums.ERole;
import com.humanresources.assistant.backend.model.authentication.Role;
import com.humanresources.assistant.backend.model.authentication.User;
import com.humanresources.assistant.backend.payload.request.LoginRequest;
import com.humanresources.assistant.backend.payload.request.SignUpRequest;
import com.humanresources.assistant.backend.payload.response.JwtResponse;
import com.humanresources.assistant.backend.payload.response.MessageResponse;
import com.humanresources.assistant.backend.repository.authentication.IRoleRepository;
import com.humanresources.assistant.backend.repository.authentication.IUserRepository;
import com.humanresources.assistant.backend.security.jwt.JwtUtils;
import com.humanresources.assistant.backend.security.services.UserDetailsImpl;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping ("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping ("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        final UsernamePasswordAuthenticationToken authenticationDetails = new UsernamePasswordAuthenticationToken(
            loginRequest.getUsername(),
            loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        final JwtResponse jwtResponse = new JwtResponse(jwt, userDetails);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping ("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Email is already in use!"));
        }

        final String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());
        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), encodedPassword);

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole =
                roleRepository
                    .findByName(ERole.USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "hr":
                        Role modRole = roleRepository.findByName(ERole.HR)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.HR_ASSISTANT)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
