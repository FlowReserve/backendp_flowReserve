package com.flowreserve.demo1.model.Response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flowreserve.demo1.model.Request.Request;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Response {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JsonIgnore
    @JoinColumn(name = "request_id")
    private Request request;
    private String nombreArchivoRespuesta;
    private LocalDateTime fechaCreacion;
    private String rutaCompletaArchivo;

}
