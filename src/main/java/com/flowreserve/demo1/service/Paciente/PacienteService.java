package com.flowreserve.demo1.service.Paciente;

import com.flowreserve.demo1.dto.Paciente.PacienteDTO;
import com.flowreserve.demo1.mapper.PacienteMapper;
import com.flowreserve.demo1.repository.Medico.MedicoRepository;
import com.flowreserve.demo1.service.Medico.MedicoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.flowreserve.demo1.model.Medico.Medico;
import com.flowreserve.demo1.model.Paciente.Paciente;
import com.flowreserve.demo1.repository.Paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PacienteService {
    private final PacienteRepository pacienteRepository;


    private final MedicoService medicoService;

    private  final MedicoRepository medicoRepository;

  private final PacienteMapper pacienteMapper;


    public Paciente crearPaciente(PacienteDTO pacienteDTO) {
//realizar jwt para probar sesiones con postman faltará medico con sesión iniciada

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailMedico = auth.getName(); // El email del usuario autenticado

        Medico medico = medicoService.findByEmail(emailMedico); // Usa el método correcto del servicio
        Long idMedico = medico.getId(); // Ya puedes obtener el ID si lo necesitas

//
//        Paciente paciente = new Paciente();
//        paciente.setNombre(pacienteDTO.getNombre());
//        paciente.setApellido(pacienteDTO.getApellido());
//        paciente.setNhc(pacienteDTO.getCodigoNHC());
//        paciente.setMedico(medico);

         Paciente paciente =  pacienteMapper.toPacienteModel(pacienteDTO);
         paciente.setMedico(medico);
        paciente = pacienteRepository.save(paciente);

        return pacienteRepository.save(paciente);
    }

    public Paciente findById(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + id));
    }

    /**
     * Busca un paciente en la base de datos, es necesario que ese paciente esté asociado con su médico.
     * @param idPaciente identificador del paciente que se quiere buscar en la BBDD
     * @param idMedico identificador del médico asociado con el médico.
     * @return Paciente o lanza un error en caso de no encontrarse.
     */
    public Paciente findPacienteByIdAndMedicoId(Long idPaciente, Long idMedico){
        return pacienteRepository.findByIdAndMedicoId(idPaciente, idMedico).orElseThrow(
                () -> new EntityNotFoundException("Paciente con id: " + idPaciente + " no ha sido encontrado para el medico: " + idMedico));

    }

    public Page<Paciente> obtenerPacientesPorMedicoAutenticado(Pageable pageable) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // Este es el username/email del médico autenticado

        Medico medico = medicoRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado"));

        return pacienteRepository.findByMedicoId(medico.getId(), pageable);
    }




}


