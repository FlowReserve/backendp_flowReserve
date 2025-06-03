package com.flowreserve.demo1.dto.authentication;

import jakarta.validation.constraints.NotBlank;

/**
 * Record para manejar los datos de login que se envian desde el front-end
 * @param email email del usuario que se va a registrar
 * @param password contraseña del usuario que se va a registrar
 */
public record AuthLoginRequestDTO(
        @NotBlank String email,
        @NotBlank String password) { }

