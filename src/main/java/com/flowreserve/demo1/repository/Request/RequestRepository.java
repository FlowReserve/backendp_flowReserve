package com.flowreserve.demo1.repository.Request;

import com.flowreserve.demo1.model.Request.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {

    Page<Request> findByMedicoId(Long MedicoId, Pageable pageable);
   Page<Request>  findByPacienteId(Long PacienteId, Pageable pageable);
    Page<Request> findByPaciente_IdAndMedico_Id(Long pacienteId, Long medicoId, Pageable pageable);
}
