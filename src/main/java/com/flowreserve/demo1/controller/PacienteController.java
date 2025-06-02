package com.flowreserve.demo1.controller;
import com.flowreserve.demo1.dto.PacienteDTO;
import com.flowreserve.demo1.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/pacientes")


public class PacienteController {
    private final PacienteService pacienteService;

    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping("/crearPaciente")
    public ResponseEntity<?> crearPaciente(@Valid @RequestBody PacienteDTO pacienteDTO) {
        pacienteService.crearPaciente(pacienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("mensaje", "Paciente creado correctamente"));
    }
}
