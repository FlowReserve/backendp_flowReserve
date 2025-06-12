package com.flowreserve.demo1.dto.Request;
import com.flowreserve.demo1.dto.Paciente.PacienteResponseDTO;
import com.flowreserve.demo1.model.Request.EstadoSolicitudEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestResponseDTO {
    private EstadoSolicitudEnum state;

    private Long id;
    private String codigo;
    private String nombreArchivoZip;

    private PacienteResponseDTO paciente;

    //DTO MEDICO Y PACIENTE
}
