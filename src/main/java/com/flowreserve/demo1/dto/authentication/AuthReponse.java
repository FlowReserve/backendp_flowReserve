package com.flowreserve.demo1.dto.authentication;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.flowreserve.demo1.dto.User.UserResponseDTO;

@JsonPropertyOrder({"user","jwt"})
public record AuthReponse(
        UserResponseDTO user,
        String jwt
        ) { }
