package com.flowreserve.demo1.dto.authentication;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequest (@NotBlank String email,
                                String password){
}
