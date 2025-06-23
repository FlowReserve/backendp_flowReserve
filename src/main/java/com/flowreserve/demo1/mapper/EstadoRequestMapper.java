package com.flowreserve.demo1.mapper;

import com.flowreserve.demo1.dto.EstadoRequest.EstadoRequestDTO;
import com.flowreserve.demo1.dto.Request.RequestDTO;
import com.flowreserve.demo1.model.Estado.EstadoRequest;
import com.flowreserve.demo1.model.Request.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EstadoRequestMapper {


    public EstadoRequest toEstadoRequestModel(EstadoRequestDTO estadoRequestDTO) {
        if (estadoRequestDTO == null) return null;
        return EstadoRequest.builder()
                .fechaCambio(estadoRequestDTO.getFechaCambio())
                .comentarios(estadoRequestDTO.getComentarios())
                .state(estadoRequestDTO.getEstado())

                .build();
    }

}
