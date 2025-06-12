package com.flowreserve.demo1.model.Request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flowreserve.demo1.model.Medico.Medico;
import com.flowreserve.demo1.model.Paciente.Paciente;
import com.flowreserve.demo1.model.Response.Response;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity

public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Médico que hace la petición
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "medico_id")

    // mejor usar "medico_id" en lugar de "user_id"
    private Medico medico;

    // Paciente sobre el que se hace la petición
    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @OneToOne(mappedBy = "request", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Response response;

    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private EstadoSolicitudEnum state;

    private String pressureA;

    private String pressureB;

    private String nombreArchivoZip;

    private String campoComentarios;

    private String codigo;


    @PrePersist
    public void generarCodigo() {
        long millis = System.currentTimeMillis();
        this.codigo = "REQ-" + Long.toString(millis, 36).toUpperCase();
    }

}
