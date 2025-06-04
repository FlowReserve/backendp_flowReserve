package com.flowreserve.demo1.service.user;

import com.flowreserve.demo1.dto.authentication.AuthLoginRequest;
import com.flowreserve.demo1.dto.authentication.AuthReponse;
import com.flowreserve.demo1.model.UserModel;
import com.flowreserve.demo1.repository.user.UserRepositoryTest;
import com.flowreserve.demo1.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepositoryTest userRepositoryTest;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel userModel = userRepositoryTest.findUserModelByEmail(email)
                .orElseThrow(() -> {
                    System.out.println("El usuario: " + email + " no existe");
                    return new UsernameNotFoundException("El usuario " + email + " no existe");
                });

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        //Convierte los roles del usuario en un GrantedAuthoritie
        userModel.getRoleModelSet()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        userModel.getRoleModelSet().stream()
                .flatMap(role -> role.getPermissionModelSet().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        return new User(userModel.getEmail(),
                userModel.getPassword(),
                userModel.isEnabled(),
                userModel.isAccountNoExpired(),
                userModel.isCredentialNoExpired(),
                userModel.isAccountNoLocked(),
                authorityList);
    }


    public AuthReponse loginUser(AuthLoginRequest authLoginRequest) {
        String username = authLoginRequest.username();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accesToken = jwtUtils.createToken(authentication);

        AuthReponse authReponse = new AuthReponse(username, "User loged successfuly", accesToken, true);
        return authReponse;

    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);


        if (userDetails == null) {
            throw new BadCredentialsException("invalid username or password");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("invalid password");

        }

        return new UsernamePasswordAuthenticationToken(username,userDetails.getPassword(),userDetails.getAuthorities());

    }

}