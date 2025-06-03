package com.flowreserve.demo1.service.user;

import com.flowreserve.demo1.model.UserModel;
import com.flowreserve.demo1.repository.user.UserRepositoryTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepositoryTest userRepositoryTest;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userRepositoryTest.findUserModelByUsername(username)
                .orElseThrow(() -> {
                    System.out.println("El usuario: " + username + " no existe");
                    return new UsernameNotFoundException("El usuario " + username + " no existe");
                });

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        //Convierte los roles del usuario en un GrantedAuthoritie
        userModel.getRoleModelSet()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        userModel.getRoleModelSet().stream()
                .flatMap(role -> role.getPermissionModelSet().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        return new User(userModel.getUsername(),
                userModel.getPassword(),
                userModel.isEnabled(),
                userModel.isAccountNoExpired(),
                userModel.isCredentialNoExpired(),
                userModel.isAccountNoLocked(),
                authorityList);
    }
}
