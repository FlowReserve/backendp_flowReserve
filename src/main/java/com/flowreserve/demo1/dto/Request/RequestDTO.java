package com.flowreserve.demo1.dto.Request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestDTO {
    private String pressureA;

    private String pressureB;

    private String comentarios;

    private Long idPaciente;




}


