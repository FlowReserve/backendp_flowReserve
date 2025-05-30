package com.flowreserve.demo1.model;

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


    @ManyToOne
    @JoinColumn(name = "user_id") // Nombre de la columna en la tabla "request"
    private Medico user;

    public Medico getUser() {
        return user;
    }

    public void setUser(Medico user) {
        this.user = user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    private LocalDateTime date;
    private String state;

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

    private String pressureA;

    private String pressureB;

    public String getNombreArchivoZip() {
        return nombreArchivoZip;
    }

    public void setNombreArchivoZip(String nombreArchivoZip) {
        this.nombreArchivoZip = nombreArchivoZip;
    }

    private String nombreArchivoZip;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }







    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }









}
