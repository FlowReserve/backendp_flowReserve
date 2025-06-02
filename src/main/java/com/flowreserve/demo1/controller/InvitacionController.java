package com.flowreserve.demo1.controller;

import com.flowreserve.demo1.dto.HospitalDTO;
import com.flowreserve.demo1.dto.InvitacionDTO;
import com.flowreserve.demo1.model.Hospital;
import com.flowreserve.demo1.model.Invitacion;
import com.flowreserve.demo1.repository.HospitalRepository;
import com.flowreserve.demo1.repository.InvitacionRepository;
import com.flowreserve.demo1.service.InvitacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.ui.Model;

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