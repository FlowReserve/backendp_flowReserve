package com.flowreserve.demo1.dto.Medico;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicoDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El nombre es obligatorio")
    private String apellido;

    @NotBlank(message = "La contraseña es obligatoria")
    private String contraseña;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato válido")
    private String email;

    @NotBlank(message = "La invitación es obligatoria")
    private String codigoInvitacion;


}
