package com.flowreserve.demo1.repository;


import com.flowreserve.demo1.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByNombre(String nombre);
    Optional<Paciente> findByNhc(String nhc);
    Optional<Paciente> findById(Long id); // ✅ Correcto
}