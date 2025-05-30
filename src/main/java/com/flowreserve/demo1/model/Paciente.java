package com.flowreserve.demo1.model;
import jakarta.persistence.*;

@Entity

public class Paciente extends User{

    private String nhc;

    public String getNhc() {
        return nhc;
    }

    public void setNhc(String nhc) {
        this.nhc = nhc;
    }

    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medico;
}
