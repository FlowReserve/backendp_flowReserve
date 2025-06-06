package com.flowreserve.demo1.repository.Paciente;


import com.flowreserve.demo1.model.Paciente.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByNombre(String nombre);
    Optional<Paciente> findByNhc(String nhc);
    Optional<Paciente> findById(Long id); //


    @Query("SELECT p FROM Paciente p WHERE p.medico.id = :idMedico")
    Page<Paciente> findByMedicoId(@Param("idMedico") Long idMedico, Pageable pageable);

}