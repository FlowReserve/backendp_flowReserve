package com.flowreserve.demo1.mapper;

import com.flowreserve.demo1.dto.Paciente.PacienteDTO;
import com.flowreserve.demo1.model.Paciente.Paciente;
import org.springframework.stereotype.Component;

@Component
public class PacienteMapper {

    public PacienteDTO toPacienteDTO(Paciente paciente){
        if(paciente == null) return null;
        return PacienteDTO.builder()
                .nombre(paciente.getNombre())
                .apellido(paciente.getApellido())
                .codigoNHC(paciente.getNhc())
                .build();
    }
}
