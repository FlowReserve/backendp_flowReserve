package com.flowreserve.demo1.mapper;

import com.flowreserve.demo1.dto.Request.RequestDTO;
import com.flowreserve.demo1.dto.Request.RequestResponseDTO;
import com.flowreserve.demo1.model.Request.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RequestMapper {
    private final PacienteMapper pacienteMapper;


    public Request toRequestModel(RequestDTO requestDTO) {
        if (requestDTO == null) return null;
        return Request.builder()
                .presionSistolica(requestDTO.getPresionSistolica())
                .presionDiastolica(requestDTO.getPresionDiastolica())
                .comentarios(requestDTO.getComentarios())
                .build();
    }

    public RequestResponseDTO toRequestResponseDTO(Request request){
        if(request == null) return null;
        return RequestResponseDTO.builder()
                .id(request.getId())
                .codigo(request.getCodigo())
                .state(request.getState())
                .date(request.getDate())
                .presionSistolica(request.getPresionSistolica())
                .presionDiastolica(request.getPresionDiastolica())
                .comentarios(request.getComentarios())
                .nombreArchivoZip(request.getNombreArchivoZip())
                .paciente(pacienteMapper.toPacienteResponseDTO(request.getPaciente()))
                .build();
    }

}
