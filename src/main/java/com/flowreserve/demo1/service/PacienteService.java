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

        Paciente paciente = new Paciente();
        paciente.setNombre(pacienteDTO.getNombrePaciente());

        paciente = pacienteRepository.save(paciente);

        return paciente;
    }

}
