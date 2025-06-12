package com.flowreserve.demo1.mapper;
import com.flowreserve.demo1.mapper.PacienteMapper;

import com.flowreserve.demo1.dto.Medico.MedicoDTO;
import com.flowreserve.demo1.dto.Paciente.PacienteResponseDTO;
import com.flowreserve.demo1.dto.Request.RequestDTO;
import com.flowreserve.demo1.dto.Request.RequestResponseDTO;
import com.flowreserve.demo1.model.Medico.Medico;
import com.flowreserve.demo1.model.Paciente.Paciente;
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
                .pressureA(requestDTO.getPressureA())
                .pressureB(requestDTO.getPressureB())
                .campoComentarios(requestDTO.getComentarios())
                .build();


    }

    public RequestResponseDTO toRequestResponseDTO(Request request){
        if(request == null) return null;
        return RequestResponseDTO.builder()
                .id(request.getId())
                .codigo(request.getCodigo())
                .state(request.getState())
                .nombreArchivoZip(request.getNombreArchivoZip())
                .pacienteResponseDTO(pacienteMapper.toPacienteResponseDTO(request.getPaciente()))
                .build();
    }




}
