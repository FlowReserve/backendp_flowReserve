package com.flowreserve.demo1.service.Medico;

import com.flowreserve.demo1.dto.Medico.MedicoDTO;
import com.flowreserve.demo1.mapper.MedicoMapper;
import com.flowreserve.demo1.model.Invitacion.Invitacion;
import com.flowreserve.demo1.model.Medico.Medico;
import com.flowreserve.demo1.model.role.RoleEnum;
import com.flowreserve.demo1.model.role.RoleModel;
import com.flowreserve.demo1.repository.Invitacion.InvitacionRepository;
import com.flowreserve.demo1.repository.Medico.MedicoRepository;
import com.flowreserve.demo1.repository.RoleRepository;
import com.flowreserve.demo1.service.Email.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
 // genera constructor con todos los final



import java.util.Optional;
@RequiredArgsConstructor
@Service
public class MedicoService {

    private final MedicoRepository medicoRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final MedicoMapper medicoMapper;
    private final InvitacionRepository invitacionRepository;
    private final EmailService emailService;



@Transactional
public Medico crearMedicoDesdeInvitacion(MedicoDTO medicoDTO) {

    Invitacion invitacion = invitacionRepository.findByCodigo(medicoDTO.getCodigoInvitacion())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invitación no encontrada"));
    if (invitacion.isUsada()) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La invitación ya fue usada");
    }
    RoleModel rolMedico = roleRepo.findByRoleEnum(RoleEnum.DOCTOR)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol MEDICO no encontrado"));    //Role roleMedico = roleRepo.findByName("ROLE_MEDICO")


    Medico medico = medicoMapper.toMedicoModel(medicoDTO);
    medico.getRoleModelSet().add(rolMedico);
    medico.setHospital(invitacion.getHospital());

    medico = medicoRepo.save(medico);

invitacion.setMedico(medico);
invitacion.setUsada(true);
invitacionRepository.save(invitacion);
    // 2. Envío de email de bienvenida
//    emailService.enviarCorreo(
//            medico.getEmail(),
//            "¡Bienvenido a nuestra app!",
//            "Hola " + medico.getNombre() + ", gracias por registrarte 😊"
//    );



return medico;
}

    public Medico findByEmail(String email) {
        return medicoRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con email: " + email));
    }

    public Medico findById(Long id) {
        return medicoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con id: " + id));
    }

    public boolean authenticate(String email, String rawPassword) {
        String emailNormalized = email.trim().toLowerCase();

        Optional<Medico> medicoOpt = medicoRepo.findByEmail(emailNormalized);
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







