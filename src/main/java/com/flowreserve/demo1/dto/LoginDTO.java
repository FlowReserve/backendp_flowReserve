package com.flowreserve.demo1.dto;
import jakarta.validation.constraints.NotBlank;

public class LoginDTO {


    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @NotBlank(message = "El email de usuario es obligatorio")
    private String email;
    @NotBlank(message = "La contraseña es obligatoria")

    private String password;
}
