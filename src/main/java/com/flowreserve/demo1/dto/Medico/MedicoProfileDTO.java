package com.flowreserve.demo1.dto.Medico;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicoProfileDTO {
    private long id;
    private String nombre;
    private String apellido;
    private String email;
    private Set<String> roles;  // <-- Aquí roles como String (por ejemplo)

}
