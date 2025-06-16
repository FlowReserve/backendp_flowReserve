package com.flowreserve.demo1.dto.Request;

import com.flowreserve.demo1.model.Request.EstadoSolicitudEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoUpdateDTO {
    @NotNull(message = "El estado no puede ser nulo")
    private EstadoSolicitudEnum estado;
}