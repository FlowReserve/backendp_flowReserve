package com.flowreserve.demo1.model.Paciente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flowreserve.demo1.model.Medico.Medico;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String nhc;


    @ManyToOne
    @JoinColumn(name = "medico_id")
    @JsonIgnore
    private Medico medico;




}
