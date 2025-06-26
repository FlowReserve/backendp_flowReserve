package com.flowreserve.demo1.mapper;

import com.flowreserve.demo1.dto.EstadoRequest.EstadoRequestDTO;
import com.flowreserve.demo1.model.Estado.EstadoRequestMedico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EstadoRequestMapper {


    public EstadoRequestMedico toEstadoRequestModel(EstadoRequestDTO estadoRequestDTO) {
        if (estadoRequestDTO == null) return null;
        return EstadoRequestMedico.builder()
                .fechaCambio(estadoRequestDTO.getFechaCambio())
                .comentarios(estadoRequestDTO.getComentarios())
                .state(estadoRequestDTO.getEstado())
                .build();
    }

    public EstadoRequestDTO estadoRequestDTO(EstadoRequestMedico estadoRequest){
        if(estadoRequest == null) return null;
        return EstadoRequestDTO.builder()
                .estado(estadoRequest.getState())
                .comentarios(estadoRequest.getComentarios())
                .fechaCambio(estadoRequest.getFechaCambio())
                .build();
    }

}
