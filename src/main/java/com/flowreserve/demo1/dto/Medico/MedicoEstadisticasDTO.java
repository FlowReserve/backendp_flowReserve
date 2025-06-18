package com.flowreserve.demo1.dto.Medico;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicoEstadisticasDTO {
    private Long total;
    private Long enCurso;
    private Long finalizadas;
    private Long pendientes;
    private Long canceladas;
    private Long totalPacientes;
}
