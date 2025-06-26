package com.flowreserve.demo1.model.Estado;

import com.flowreserve.demo1.model.Admin.Admin;
import com.flowreserve.demo1.model.Request.EstadoSolicitudEnum;
import com.flowreserve.demo1.model.Request.Request;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class EstadoRequestMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private LocalDateTime fechaCambio;

    private String comentarios;

    @Enumerated(EnumType.STRING)
    private EstadoSolicitudEnum state;

    // Relación con la entidad Request (muchos estados pueden pertenecer a una solicitud)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    private Request request;


}