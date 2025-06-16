package com.flowreserve.demo1.service.user;

import com.flowreserve.demo1.dto.User.UserResponseDTO;
import com.flowreserve.demo1.dto.authentication.AuthLoginRequest;
import com.flowreserve.demo1.dto.authentication.AuthReponse;
import com.flowreserve.demo1.model.User.User;
import com.flowreserve.demo1.repository.user.UserRepository;
import com.flowreserve.demo1.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.flowreserve.demo1.mapper.UserMapper;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userDAO = userRepository.findByEmail(email)
               .orElseThrow(() -> {
                    System.out.println("El usuario: " + email + " no existe");
                    return new UsernameNotFoundException("El usuario " + email + " no existe");
                });

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        //Convierte los roles del usuario en un GrantedAuthoritie

        userDAO.getRoleModelSet()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        userDAO.getRoleModelSet().stream()
                .flatMap(role -> role.getPermissionModelSet().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        return new org.springframework.security.core.userdetails.User(userDAO.getEmail(),
                userDAO.getPassword(),
                userDAO.isEnabled(),
                userDAO.isAccountNoExpired(),
                userDAO.isCredentialNoExpired(),
                userDAO.isAccountNoLocked(),
                authorityList);
    }


    /**
     * Funcion que maneja el login de un usuario al sistema.
     * @param authLoginRequest solicitud de datos necesarios para realizar el login del usuario
     * @return
     */
    public AuthReponse loginUser(AuthLoginRequest authLoginRequest) {
        String email = authLoginRequest.email();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accesToken = jwtUtils.createToken(authentication);

        User usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));


        UserResponseDTO userDto = userMapper.toUserDTO(usuario);
        return new AuthReponse(userDto, accesToken);
    }

    public Authentication authenticate(String email, String password) {
        UserDetails userDetails = this.loadUserByUsername(email);


        if (userDetails == null) {
            throw new BadCredentialsException("invalid username or password");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("invalid password");

        }

        return new UsernamePasswordAuthenticationToken(email,userDetails.getPassword(),userDetails.getAuthorities());

    }

}