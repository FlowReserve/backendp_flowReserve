package com.flowreserve.demo1.controller.Medico;

import com.flowreserve.demo1.dto.Medico.MedicoDTO;
import com.flowreserve.demo1.model.Medico.Medico;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import com.flowreserve.demo1.service.Medico.MedicoService;
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
    public ResponseEntity<?> crearMedico(@Valid @RequestBody MedicoDTO medicoDTO) {
        try {
            Medico medicoCreado = medicoService.crearMedicoDesdeInvitacion(medicoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(medicoCreado);
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getReason());
        }
    }


}













