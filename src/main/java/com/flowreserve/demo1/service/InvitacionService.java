package com.flowreserve.demo1.service;

import com.flowreserve.demo1.dto.InvitacionDTO;
import com.flowreserve.demo1.model.Hospital;
import com.flowreserve.demo1.model.Invitacion;
import com.flowreserve.demo1.repository.HospitalRepository;
import com.flowreserve.demo1.repository.InvitacionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service

public class InvitacionService
{

    @Autowired
    private InvitacionRepository invitacionRepository;

    @Autowired
    private HospitalRepository hospitalRepository;


    private static final int MAX_INTENTOS = 10;
    @Transactional
    public InvitacionDTO crearInvitacion(Long hospitalId) {

        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new RuntimeException("Hospital no encontrado"));


        Invitacion invitacion = new Invitacion();
        invitacion.setHospital(hospital);
        invitacion.setUsada(false);

        // Generar código único
        String codigo = generarCodigoUnico();
        invitacion.setCodigo(codigo);
        invitacionRepository.save(invitacion);

        InvitacionDTO dto = new InvitacionDTO();
        dto.setHospitalId(hospital.getId());
        dto.setCodigo(invitacion.getCodigo());
        dto.setUsada(invitacion.isUsada());

        return dto;
    }
    private String generarCodigoUnico() {
        for (int i = 0; i < MAX_INTENTOS; i++) {
            String codigo = "INV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            boolean existe = invitacionRepository.existsByCodigo(codigo);
            if (!existe) {
                return codigo;
            }
        }
        throw new RuntimeException("No se pudo generar un código único después de " + MAX_INTENTOS + " intentos");
    }

    public Invitacion guardarInvitacion(Invitacion invitacion) {
        return invitacionRepository.save(invitacion);
    }
}
