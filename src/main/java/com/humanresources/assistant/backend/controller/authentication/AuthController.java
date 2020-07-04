package com.humanresources.assistant.backend.controller.authentication;

import static com.humanresources.assistant.backend.enums.ERole.ADMIN;
import static com.humanresources.assistant.backend.enums.ERole.HR;
import static com.humanresources.assistant.backend.enums.ERole.HR_ASSISTANT;
import static com.humanresources.assistant.backend.enums.ERole.USER;

import com.humanresources.assistant.backend.entity.authentication.RoleEntity;
import com.humanresources.assistant.backend.entity.authentication.UserEntity;
import com.humanresources.assistant.backend.payload.request.LoginRequest;
import com.humanresources.assistant.backend.payload.request.SignUpRequest;
import com.humanresources.assistant.backend.payload.response.JwtResponse;
import com.humanresources.assistant.backend.payload.response.MessageResponse;
import com.humanresources.assistant.backend.repository.authentication.IRoleRepository;
import com.humanresources.assistant.backend.repository.authentication.UserRepository;
import com.humanresources.assistant.backend.security.jwt.JwtUtils;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class AuthController {

    private final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @RequestMapping (method = RequestMethod.POST, value = "/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        final UsernamePasswordAuthenticationToken authenticationDetails = new UsernamePasswordAuthenticationToken(
            loginRequest.getUsername(),
            loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        final JwtResponse jwtResponse = new JwtResponse(jwt);
        return ResponseEntity.ok(jwtResponse);
    }

    @RequestMapping (method = RequestMethod.POST, value = "/signup")
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

        if (signUpRequest.getRoles().isEmpty()) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: User should have at least one role!"));
        }

        final String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());
        UserEntity user = new UserEntity(signUpRequest.getUsername(), signUpRequest.getEmail(), encodedPassword);

        Set<String> requestRole = signUpRequest.getRoles();
        Set<RoleEntity> roles = new HashSet<>();

        if (requestRole == null) {
            RoleEntity userRole =
                roleRepository
                    .findByNameLike(USER.toString())
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            requestRole.forEach(role -> {
                switch (role) {
                    case "admin":
                        RoleEntity adminRole = roleRepository.findByNameLike(ADMIN.toString())
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "hr":
                        RoleEntity modRole = roleRepository.findByNameLike(HR.toString())
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    default:
                        RoleEntity userRole = roleRepository.findByNameLike(HR_ASSISTANT.toString())
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                        break;
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
