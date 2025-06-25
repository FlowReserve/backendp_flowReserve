package com.flowreserve.demo1.dto.Request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestDTO {

    @Min(0)
    @Max(300)
    private int presionSistolica;

    @Min(0)
    @Max(300)
    private int presionDiastolica;

    private String comentarios;

    @NotNull(message = "idPaciente no puede ser null")
    private Long idPaciente;

    @NotBlank(message = "Lesiones no puede ser blank")
    private String lesiones;

    private String lesionesPersonalizadas;

}


