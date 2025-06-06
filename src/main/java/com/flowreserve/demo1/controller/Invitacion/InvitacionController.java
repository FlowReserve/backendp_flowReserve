package com.flowreserve.demo1.controller.Invitacion;

import com.flowreserve.demo1.dto.Invitacion.InvitacionDTO;
import com.flowreserve.demo1.repository.Hospital.HospitalRepository;
import com.flowreserve.demo1.repository.Invitacion.InvitacionRepository;
import com.flowreserve.demo1.service.Invitacion.InvitacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/invitaciones")
public class InvitacionController {

    @Autowired
    private InvitacionRepository invitacionRepository;


    @Autowired
    private InvitacionService invitacionService;

    @Autowired
    private HospitalRepository hospitalRepository;


    @PostMapping
    public ResponseEntity<?> crearInvitacion(@Valid @RequestBody InvitacionDTO invitacionDTO) {
        InvitacionDTO respuesta = invitacionService.crearInvitacion(invitacionDTO.getHospitalId());
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }
}