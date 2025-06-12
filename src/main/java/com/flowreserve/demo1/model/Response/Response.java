package com.flowreserve.demo1.model.Response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flowreserve.demo1.model.Request.Request;
import jakarta.persistence.*;

import java.time.LocalDateTime;

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


    public Long getId() {
        return id;
    }


    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public String getNombreArchivoRespuesta() {
        return nombreArchivoRespuesta;
    }

    public void setNombreArchivoRespuesta(String nombreArchivoRespuesta) {
        this.nombreArchivoRespuesta = nombreArchivoRespuesta;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getRutaCompletaArchivo() {
        return rutaCompletaArchivo;
    }

    public void setRutaCompletaArchivo(String rutaCompletaArchivo) {
        this.rutaCompletaArchivo = rutaCompletaArchivo;
    }
}
