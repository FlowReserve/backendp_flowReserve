package com.flowreserve.demo1.controller;

import com.flowreserve.demo1.dto.LoginDTO;
import com.flowreserve.demo1.service.MedicoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/")

public class LoginController {

    private final MedicoService medicoService;

    public LoginController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {



            boolean authenticated = medicoService.authenticate(loginDTO.getUsername(), loginDTO.getPassword());

            if (authenticated) {
                return ResponseEntity.ok(Map.of("mensaje", "Login exitoso"));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Credenciales inválidas"));
            }
        }




}
