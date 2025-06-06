package com.flowreserve.demo1.controller.Paciente;
import com.flowreserve.demo1.dto.Paciente.PacienteDTO;
import com.flowreserve.demo1.model.Paciente.Paciente;
import com.flowreserve.demo1.service.Paciente.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/pacientes")


public class PacienteController {
    private final PacienteService pacienteService;

    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping("/new")
    public ResponseEntity<?> crearPaciente(@Valid @RequestBody PacienteDTO pacienteDTO) {
        pacienteService.crearPaciente(pacienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("mensaje", "Paciente creado correctamente"));
    }

    @GetMapping("/mis-pacientes")
    public ResponseEntity<Page<Paciente>> listarPacientesDelMedicoAutenticado(
            @PageableDefault(size = 10, sort = "apellido") Pageable pageable) {

        Page<Paciente> pacientes = pacienteService.obtenerPacientesPorMedicoAutenticado(pageable);
        return ResponseEntity.ok(pacientes);
    }

}
