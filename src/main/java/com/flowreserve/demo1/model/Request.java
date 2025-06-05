package com.flowreserve.demo1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private LocalDateTime date;

    private String state;

    private String pressureA;

    private String pressureB;

    private String nombreArchivoZip;

    private String campoComentarios;

    private String codigo;


    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPressureA() {
        return pressureA;
    }

    public void setPressureA(String pressureA) {
        this.pressureA = pressureA;
    }

    public String getPressureB() {
        return pressureB;
    }

    public void setPressureB(String pressureB) {
        this.pressureB = pressureB;
    }

    public String getNombreArchivoZip() {
        return nombreArchivoZip;
    }

    public void setNombreArchivoZip(String nombreArchivoZip) {
        this.nombreArchivoZip = nombreArchivoZip;
    }

    public String getCampoComentarios() {
        return campoComentarios;
    }

    public void setCampoComentarios(String campoComentarios) {
        this.campoComentarios = campoComentarios;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @PrePersist
    public void generarCodigo() {
        long millis = System.currentTimeMillis();
        this.codigo = "REQ-" + Long.toString(millis, 36).toUpperCase();
    }

}
