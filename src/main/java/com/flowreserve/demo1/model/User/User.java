package com.flowreserve.demo1.model.User;
import com.flowreserve.demo1.model.role.RoleModel;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Inheritance(strategy = InheritanceType.JOINED) // o SINGLE_TABLE o TABLE_PER_CLASS según lo que prefieras
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String apellido;

    @Column(unique = true)
    private String email;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles_test", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleModel> roleModelSet = new HashSet<>();

    @Column(name="is_enabled")
    private boolean isEnabled;
    @Column(name="account_no_expired")
    private boolean accountNoExpired;

    @Column(name="account_no_locked")
    private boolean accountNoLocked;

    @Column(name="credential_no_expired")
    private boolean credentialNoExpired;

}
