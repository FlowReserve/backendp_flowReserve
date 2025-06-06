package com.flowreserve.demo1.controller.Login;

import com.flowreserve.demo1.service.Medico.MedicoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/api/v1/")
@RequestMapping("/acceso")

public class LoginController {

    private final MedicoService medicoService;

    public LoginController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @GetMapping("/formulario")
    public String mostrarFormularioLogin() {
        return "accesoLogin"; // plantilla accesoLogin.html
    }

/*
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        boolean authenticated = medicoService.authenticate(loginDTO.getEmail(), loginDTO.getPassword());

        if (authenticated) {
            return ResponseEntity.ok(Map.of("mensaje", "Login exitoso"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales inválidas"));
        }
    }
*/



}
