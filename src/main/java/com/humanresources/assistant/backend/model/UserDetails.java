package com.humanresources.assistant.backend.model;

import com.humanresources.assistant.backend.enums.ERole;
import java.util.List;
import lombok.Builder;

@Builder
public class UserDetails {

    private final Integer id;

    private final String username;

    private final String email;

    private final List<ERole> roles;
}
