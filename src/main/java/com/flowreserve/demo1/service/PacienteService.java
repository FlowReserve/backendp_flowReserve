package com.flowreserve.demo1.service;

import com.flowreserve.demo1.dto.PacienteDTO;

import com.flowreserve.demo1.model.Paciente;
import com.flowreserve.demo1.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class PacienteService {
    private final PacienteRepository pacienteRepository;
    @Autowired
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente crearPaciente(PacienteDTO pacienteDTO) {
//realizar jwt para probar sesiones con postman faltará medico con sesión iniciada
        Paciente paciente = new Paciente();
        paciente.setNombre(pacienteDTO.getNombrePaciente());
         paciente.setApellido(pacienteDTO.getApellidoPaciente());
         paciente.setNhc(pacienteDTO.getCodigoCNHC());
        paciente = pacienteRepository.save(paciente);

        return paciente;
    }

}
