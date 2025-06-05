package com.flowreserve.demo1.service;

import com.flowreserve.demo1.dto.MedicoDTO;
import com.flowreserve.demo1.model.Invitacion;
import com.flowreserve.demo1.model.Medico;
import com.flowreserve.demo1.model.Role;
import com.flowreserve.demo1.repository.InvitacionRepository;
import com.flowreserve.demo1.repository.MedicoRepository;
import com.flowreserve.demo1.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MedicoService(MedicoRepository medicoRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder) {
        this.medicoRepo = medicoRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private InvitacionRepository invitacionRepository;

@Transactional
public Medico crearMedicoDesdeInvitacion(MedicoDTO medicoDTO) {

    Invitacion invitacion = invitacionRepository.findByCodigo(medicoDTO.getCodigoInvitacion())
            .orElseThrow(() -> new RuntimeException("Invitación no encontrada"));
    if (invitacion.isUsada()) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invitación ya fue usada");
    }

    Medico medico = new Medico();
    medico.setNombre(medicoDTO.getNombre());
    medico.setApellido(medicoDTO.getApellido());
    medico.setEmail(medicoDTO.getEmail());
    medico.setPassword(passwordEncoder.encode(medicoDTO.getContraseña()));

    //Role roleMedico = roleRepo.findByName("ROLE_MEDICO")
     //       .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

   // medico.getRoles().add(roleMedico);
    medico.setHospital(invitacion.getHospital());

    medico = medicoRepo.save(medico);

invitacion.setMedico(medico);
invitacion.setUsada(true);
invitacionRepository.save(invitacion);

return medico;
}


    @Autowired
    private MedicoRepository medicoRepository;
    public Medico findByEmail(String email) {
        return medicoRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con email: " + email));
    }

    public Medico findById(Long id) {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con id: " + id));
    }

    public boolean authenticate(String email, String rawPassword) {
        String emailNormalized = email.trim().toLowerCase();

        Optional<Medico> medicoOpt = medicoRepository.findByEmail(emailNormalized);
        if (medicoOpt.isEmpty()) {
            System.out.println("No se encontró médico con email: " + emailNormalized);
            return false;
        }

        Medico medico = medicoOpt.get();

        if (medico.getPassword() == null) {
            System.out.println("El medico no tiene contraseña almacenada.");
            return false;
        }

        boolean matches = passwordEncoder.matches(rawPassword, medico.getPassword());
        System.out.println("Resultado matches: " + matches);
        return matches;
    }



    }







