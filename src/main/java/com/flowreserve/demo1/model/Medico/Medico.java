package com.flowreserve.demo1.model.Medico;

import com.flowreserve.demo1.model.Hospital.Hospital;
import com.flowreserve.demo1.model.Paciente.Paciente;
import com.flowreserve.demo1.model.Request.Request;
import com.flowreserve.demo1.model.User.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Medico extends User {

    private String especialidad;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Paciente> pacientes;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Request> requests;

}