package com.flowreserve.demo1.mapper;

import com.flowreserve.demo1.dto.Medico.MedicoDTO;
import com.flowreserve.demo1.dto.Medico.MedicoProfileDTO;
import com.flowreserve.demo1.dto.Medico.MedicoResponseDTO;
import com.flowreserve.demo1.dto.Paciente.PacienteResponseDTO;
import com.flowreserve.demo1.model.Medico.Medico;
import com.flowreserve.demo1.model.Paciente.Paciente;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MedicoMapper {
    private final PasswordEncoder passwordEncoder;

    /**
     * Transforma una entidad Medico en una entidad de MedicoProfileDTO
     * @param medico entidad que se quiere convertir
     * @return MedicoProfileDTO
     */
    public Medico toMedicoModel(MedicoDTO medicoDTO) {
        if (medicoDTO == null) return null;
        return Medico.builder()
                .nombre(medicoDTO.getNombre())
                .apellido(medicoDTO.getApellido())
                .email(medicoDTO.getEmail())
                .password(passwordEncoder.encode(medicoDTO.getContraseña()))
                .build();
    }






    public MedicoProfileDTO toMedicoProfileDTO(Medico medico){
        if(medico == null) return null;
        return MedicoProfileDTO.builder()
                .email(medico.getEmail())
                .apellido(medico.getApellido())
                .nombre(medico.getNombre())
                .build();
    }

}
