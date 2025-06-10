package com.flowreserve.demo1.dto.Paciente;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PacienteResponseDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String codigoNHC;
}
