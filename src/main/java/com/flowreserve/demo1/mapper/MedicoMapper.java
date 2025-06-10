package com.flowreserve.demo1.mapper;

import com.flowreserve.demo1.dto.Medico.MedicoProfileDTO;
import com.flowreserve.demo1.model.Medico.Medico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MedicoMapper {

    /**
     * Transforma una entidad Medico en una entidad de MedicoProfileDTO
     * @param medico entidad que se quiere convertir
     * @return MedicoProfileDTO
     */
    public MedicoProfileDTO toMedicoProfileDTO(Medico medico){
        if(medico == null) return null;
        return MedicoProfileDTO.builder()
                .email(medico.getEmail())
                .apellido(medico.getApellido())
                .nombre(medico.getNombre())
                .build();
    }

}
