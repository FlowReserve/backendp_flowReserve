package com.flowreserve.demo1.dto.Request;
import com.flowreserve.demo1.dto.EstadoRequest.EstadoRequestDTO;
import com.flowreserve.demo1.dto.Paciente.PacienteResponseDTO;
import com.flowreserve.demo1.model.Request.EstadoSolicitudEnum;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestResponseDTO {

    private Long id;
    private String codigo;
    private String nombreArchivoZip;
    private LocalDateTime date;
    private EstadoSolicitudEnum state;
    private Set<EstadoRequestDTO> listadoEstados;
    private PacienteResponseDTO paciente;
    private String comentarios;
    private int presionSistolica;
    private int presionDiastolica;
    private String lesiones;
    private String lesionesPersonalizadas;

}
