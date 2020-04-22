package com.humanresources.assistant.backend.payload.request;

import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignUpRequest {

    @NotBlank
    @Size (min = 3, max = 20, message = "Username should contain at least 3 characters, max length is 20")
    private String username;

    @NotBlank
    @Size (max = 50, message = "Invalid email length")
    private String email;

    private Set<String> roles;

    @NotBlank
    @Size (min = 6, max = 20, message = "Password should contain at least 6 characters, max length is 20")
    private String password;

}
