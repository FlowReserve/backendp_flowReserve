package com.flowreserve.demo1.model.Paciente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flowreserve.demo1.model.Medico.Medico;
import jakarta.persistence.*;

@Entity

public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String nombre;
    private String apellido;

    private String nhc;

    public String getNhc() {
        return nhc;
    }

    public void setNhc(String nhc) {
        this.nhc = nhc;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    @ManyToOne
    @JoinColumn(name = "medico_id")
    @JsonIgnore
    private Medico medico;




}
