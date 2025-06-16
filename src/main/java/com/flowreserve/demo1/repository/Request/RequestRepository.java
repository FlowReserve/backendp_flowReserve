package com.flowreserve.demo1.repository.Request;

import com.flowreserve.demo1.model.Request.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RequestRepository extends JpaRepository<Request, Long> {

    Page<Request> findByMedicoId(Long MedicoId, Pageable pageable);
   Page<Request>  findByPacienteId(Long PacienteId, Pageable pageable);
    Page<Request> findByPaciente_IdAndMedico_Id(Long pacienteId, Long medicoId, Pageable pageable);

    @Query("SELECT COUNT(r) FROM Request r WHERE r.medico.id = :medicoId")
    Long countTotalByMedico(@Param("medicoId") Long medicoId);

    @Query("SELECT COUNT(r) FROM Request r WHERE r.medico.id = :medicoId AND r.state = 'EN_PROCESO'")
    Long countEnCursoByMedico(@Param("medicoId") Long medicoId);

    @Query("SELECT COUNT(r) FROM Request r WHERE r.medico.id = :medicoId AND r.state = 'COMPLETADA'")
    Long countFinalizadasByMedico(@Param("medicoId") Long medicoId);


    @Query("SELECT COUNT(r) FROM Request r WHERE r.medico.id = :medicoId AND r.state = 'PENDIENTE'")
    Long countPendientesByMedico(@Param("medicoId") Long medicoId);

    @Query("SELECT COUNT(r) FROM Request r WHERE r.medico.id = :medicoId AND r.state = 'CANCELADA'")
    Long countCanceladasByMedico(@Param("medicoId") Long medicoId);


}
