package com.flowreserve.demo1.mapper;

import com.flowreserve.demo1.dto.Paciente.PacienteDTO;
import com.flowreserve.demo1.dto.Paciente.PacienteResponseDTO;
import com.flowreserve.demo1.model.Paciente.Paciente;
import org.springframework.stereotype.Component;

@Component
public class PacienteMapper {

    public Paciente toPacienteModel(PacienteDTO pacienteDTO){
        if(pacienteDTO == null) return null;
        return Paciente.builder()
                .nombre(pacienteDTO.getNombre())
                .apellido(pacienteDTO.getApellido())
                .nhc(pacienteDTO.getCodigoNHC())
                .build();
    }

    public PacienteResponseDTO toPacienteResponseDTO(Paciente paciente) {
        if (paciente == null) return null;
        return PacienteResponseDTO.builder()
                .id(paciente.getId())
                .nombre(paciente.getNombre())
                .apellido(paciente.getApellido())
                .codigoNHC(paciente.getNhc())
                .build();
    }

}
