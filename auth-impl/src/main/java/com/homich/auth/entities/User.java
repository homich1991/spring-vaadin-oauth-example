package com.homich.auth.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name", name = "UNIQUE_USER_NAME")
})
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class User extends Model {

    @NotNull(message = "User name should be set")
    @NotEmpty(message = "User name should be set")
    private String name;

    @NotNull(message = "User password should be set")
    @NotEmpty(message = "User password should be set")
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean isEnabled = true;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_ROLE_USER")))
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<Role> roles;

    public User(String name, String password) {
        this.name = name;
        this.password = new BCryptPasswordEncoder().encode(password);
    }
}
