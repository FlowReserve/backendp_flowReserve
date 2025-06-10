package com.flowreserve.demo1.dto.Medico;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicoProfileDTO {
    private String nombre;
    private String apellido;
    private String email;

}
