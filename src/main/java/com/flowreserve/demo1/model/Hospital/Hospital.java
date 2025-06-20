package com.flowreserve.demo1.model.Hospital;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flowreserve.demo1.model.Medico.Medico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Hospital  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    private String nombre;


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<Medico> getMedicos() {
        return medicos;
    }

    public void setMedicos(List<Medico> medicos) {
        this.medicos = medicos;
    }

    @Column(unique = true, nullable = false)
    private String codigo;

    //evitar que se repitan los datos del Medico muchas veces en la respuesta (por ejemplo, al incluir el Hospital que a su vez tiene más Medico),
    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Medico> medicos;

}
