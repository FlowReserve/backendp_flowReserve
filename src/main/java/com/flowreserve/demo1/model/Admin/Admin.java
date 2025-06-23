package com.flowreserve.demo1.model.Admin;

import com.flowreserve.demo1.model.Estado.EstadoRequest;
import com.flowreserve.demo1.model.User.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Admin extends User {


    private  String departamento;
    // Opcional: historial de cambios de estado hechos por este admin
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EstadoRequest> estadosCambiados = new ArrayList<>();
}
