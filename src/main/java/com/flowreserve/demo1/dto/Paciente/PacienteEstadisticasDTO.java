package com.flowreserve.demo1.dto.Paciente;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacienteEstadisticasDTO {
    private Long total;
    private Long enCurso;
    private Long finalizadas;
    private Long pendientes;
    private Long canceladas;
}
