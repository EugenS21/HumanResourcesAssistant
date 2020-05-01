package com.humanresources.assistant.backend.entity.authentication;

import static com.humanresources.assistant.backend.entity.authentication.UserEntity.TABLE_NAME;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name = TABLE_NAME,
        uniqueConstraints = {@UniqueConstraint (columnNames = "username"), @UniqueConstraint (columnNames = "password")})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity {

    protected static final String TABLE_NAME = "t_user";

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @ManyToMany (fetch = FetchType.LAZY)
    @JoinTable (name = "t_user_role",
                joinColumns = @JoinColumn (name = "user_id"),
                inverseJoinColumns = @JoinColumn (name = "role_id"))
    private Set<RoleEntity> roles;

    public UserEntity(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
