package com.flowreserve.demo1.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;

    private String nombreArchivoRespuesta;

    private LocalDateTime fechaCreacion;


    private String rutaCompletaArchivo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
