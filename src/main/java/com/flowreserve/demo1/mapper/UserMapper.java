package com.flowreserve.demo1.mapper;

import com.flowreserve.demo1.dto.Medico.MedicoProfileDTO;
import com.flowreserve.demo1.dto.User.UserResponseDTO;
import com.flowreserve.demo1.model.Medico.Medico;
import com.flowreserve.demo1.model.User.User;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Component
public class UserMapper {

    public UserResponseDTO toUserDTO(User user){
        if(user == null) return null;

        // Asegúrate de importar: java.util.stream.Collectors
        List<String> roles = user.getRoleModelSet()
                .stream()
                .map(role -> role.getRoleEnum().name()) // Cambia getName() según tu clase RoleModel
                .collect(Collectors.toList());


        return UserResponseDTO.builder()
                .id(user.getId())
                .nombre(user.getNombre())
                .email(user.getEmail())
                .roles(roles)
                .build();
    }









}
