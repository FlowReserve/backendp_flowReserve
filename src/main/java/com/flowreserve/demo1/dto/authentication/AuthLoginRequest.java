package com.flowreserve.demo1.dto.authentication;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequest (@NotBlank String username,
                                String password){
}
