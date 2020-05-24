package com.humanresources.assistant.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {

    @JsonProperty ("id")
    private Long id;

    @JsonProperty ("username")
    private String username;

    @JsonProperty ("email")
    private String email;

    @JsonIgnore
    private String role;

}
