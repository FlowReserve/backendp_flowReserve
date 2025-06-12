package com.flowreserve.demo1.dto.Request;
import com.flowreserve.demo1.dto.Paciente.PacienteResponseDTO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestResponseDTO {
    private Long id;
    private String codigo;
    private String state;
    private String nombreArchivoZip;
    private LocalDateTime date;
    private PacienteResponseDTO paciente;

    //DTO MEDICO Y PACIENTE
}
