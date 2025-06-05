package com.flowreserve.demo1.service;

import com.flowreserve.demo1.dto.PacienteDTO;

import com.flowreserve.demo1.model.Medico;
import com.flowreserve.demo1.model.Paciente;
import com.flowreserve.demo1.repository.InvitacionRepository;
import com.flowreserve.demo1.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service

public class PacienteService {
    private final PacienteRepository pacienteRepository;
    @Autowired
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }
    @Autowired
    private MedicoService medicoService;

    public Paciente crearPaciente(PacienteDTO pacienteDTO) {
//realizar jwt para probar sesiones con postman faltará medico con sesión iniciada

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailMedico = auth.getName(); // El email del usuario autenticado

        Medico medico = medicoService.findByEmail(emailMedico); // Usa el método correcto del servicio
        Long idMedico = medico.getId(); // Ya puedes obtener el ID si lo necesitas



        Paciente paciente = new Paciente();
        paciente.setNombre(pacienteDTO.getNombrePaciente());
         paciente.setApellido(pacienteDTO.getApellidoPaciente());
         paciente.setNhc(pacienteDTO.getCodigoCNHC());
        paciente.setMedico(medico);
        paciente = pacienteRepository.save(paciente);

        return pacienteRepository.save(paciente);
    }
    public Paciente findById(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + id));
    }
}
