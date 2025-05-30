package com.flowreserve.demo1.controller;

import com.flowreserve.demo1.dto.InvitacionDTO;
import com.flowreserve.demo1.dto.MedicoDTO;
import com.flowreserve.demo1.model.Hospital;
import com.flowreserve.demo1.model.Invitacion;
import com.flowreserve.demo1.model.Medico;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.flowreserve.demo1.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/medicos")
public class MedicoController {



    private final MedicoService medicoService;

    @Autowired
    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }



    @PostMapping
    public ResponseEntity<?> crearMedico(@RequestBody MedicoDTO medicoDTO) {
        try {
                    Medico medicoCreado = medicoService.crearMedicoDesdeInvitacion(medicoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(medicoCreado);



        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el médico: " + e.getMessage());
        }

    }











}

