package com.flowreserve.demo1.dto.Request;

import com.flowreserve.demo1.model.Request.EstadoSolicitudEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseRequestEstadoUpdateDTO {

    private Long id;
    private String codigo;
    private EstadoSolicitudEnum estado;
}
