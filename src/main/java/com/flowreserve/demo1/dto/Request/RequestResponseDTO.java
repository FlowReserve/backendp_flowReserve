package com.flowreserve.demo1.dto.Request;
import com.flowreserve.demo1.dto.Paciente.PacienteResponseDTO;
import com.flowreserve.demo1.model.Request.EstadoSolicitudEnum;
import lombok.*;

import java.time.LocalDateTime;

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
    private LocalDateTime date;
    private PacienteResponseDTO paciente;
    private String comentarios;
    private int presionSistolica;
    private int presionDiastolica;

}
