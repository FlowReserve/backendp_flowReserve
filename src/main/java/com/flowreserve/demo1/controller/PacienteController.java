package com.flowreserve.demo1.controller;
import com.flowreserve.demo1.dto.PacienteDTO;
import com.flowreserve.demo1.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;


@RestController
@RequestMapping("/api/v1/pacientes")


public class PacienteController {
    private final PacienteService pacienteService;

    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping("/crearPaciente")
    public ResponseEntity<?> crearPaciente(@RequestBody PacienteDTO pacienteDTO) {
        try {
            pacienteService.crearPaciente(pacienteDTO);
            return ResponseEntity.ok("Paciente creado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear paciente: " + e.getMessage());
        }

    }

}
