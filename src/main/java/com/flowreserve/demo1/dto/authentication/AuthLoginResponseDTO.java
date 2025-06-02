package com.flowreserve.demo1.dto.authentication;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username", "message", "jwt", "status"})
public record AuthLoginResponseDTO(

        String username,
        String message,
        String jwt,
        boolean status) { }
