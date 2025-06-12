package com.flowreserve.demo1.service.Medico;

import com.flowreserve.demo1.controller.Medico.MedicoController;
import com.flowreserve.demo1.exceptions.CustomExceptions;
import com.flowreserve.demo1.model.Medico.Medico;
import com.flowreserve.demo1.repository.Medico.MedicoRepository;
import jakarta.persistence.EntityNotFoundException;
import jdk.swing.interop.SwingInterOpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ObtenerMedicoService {

    private final MedicoRepository medicoRepository;

    /**
     * Obtiene la información correspondiente a un médico solicitada a través de un ID
     * @param id identificador del médico del que se quieren obtener los datos
     * @return Objeto Medico extraído de la BBDD o error en caso de no existir.
     */
    public Medico obtenerMedicoPorId(Long id){
        return medicoRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Médico con ID " + id + " no fue encontrado en la Base de datos"));
    }

    public Medico obtenerMedicoPorMail(String email){
        return medicoRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("Medico con mail:" + email + " no ha sido encontrado")
        );
    }

    public Long obtenerIdMedicoPorMail(String email){
        return medicoRepository.findIdByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("Medico con email: " + email + " no ha sido encontrado"));

    }

}
