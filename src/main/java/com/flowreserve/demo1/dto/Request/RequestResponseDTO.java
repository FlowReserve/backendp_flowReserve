package com.flowreserve.demo1.dto.Request;
import com.flowreserve.demo1.dto.Paciente.PacienteResponseDTO;
import lombok.*;

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

    private PacienteResponseDTO paciente;

    //DTO MEDICO Y PACIENTE
}
