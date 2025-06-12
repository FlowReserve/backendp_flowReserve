package com.flowreserve.demo1.dto.Medico;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicoResponseDTO {
    private String nombre;
    private String apellido;
    private String email;
}
