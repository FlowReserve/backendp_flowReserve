package com.flowreserve.demo1.mapper;

import com.flowreserve.demo1.dto.Medico.MedicoDTO;
import com.flowreserve.demo1.dto.Request.RequestDTO;
import com.flowreserve.demo1.model.Medico.Medico;
import com.flowreserve.demo1.model.Request.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RequestMapper {


    public Request toRequestModel(RequestDTO requestDTO) {
        if (requestDTO == null) return null;
        return Request.builder()
                .pressureA(requestDTO.getPressureA())
                .pressureB(requestDTO.getPressureB())
                .campoComentarios(requestDTO.getComentarios())
                .build();


    }




}
