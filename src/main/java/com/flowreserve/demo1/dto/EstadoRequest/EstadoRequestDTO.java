package com.flowreserve.demo1.dto.EstadoRequest;

import com.flowreserve.demo1.model.Request.EstadoSolicitudEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstadoRequestDTO {
    private String comentarios;
    private EstadoSolicitudEnum estado;
    private LocalDateTime fechaCambio;

}
