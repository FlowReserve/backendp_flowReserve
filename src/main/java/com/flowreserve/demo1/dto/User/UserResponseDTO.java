package com.flowreserve.demo1.dto.User;

import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    private Long id;
    private String nombre;
    private String email;
    private List<String> roles;
}
