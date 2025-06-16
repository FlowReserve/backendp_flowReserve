package com.flowreserve.demo1.dto.Request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestDTO {

    private int presionSistolica;
    private int presionDiastolica;
    private String comentarios;
    private long idPaciente;

}


