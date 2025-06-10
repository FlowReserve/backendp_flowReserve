/*
package com.flowreserve.demo1.model;

import com.flowreserve.demo1.model.role.RoleModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users_test")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String lastname ;


    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles_test", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleModel> roleModelSet = new HashSet<>();

    @Column(unique = true)
    private String email;

    @Column(name="is_enabled")
    private boolean isEnabled;
    @Column(name="account_no_expired")
    private boolean accountNoExpired;

    @Column(name="account_no_locked")
    private boolean accountNoLocked;

    @Column(name="credential_no_expired")
    private boolean credentialNoExpired;
}
*/