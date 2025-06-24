package com.flowreserve.demo1.repository.Request;

import com.flowreserve.demo1.dto.Medico.MedicoEstadisticasDTO;
import com.flowreserve.demo1.model.Request.EstadoSolicitudEnum;
import com.flowreserve.demo1.dto.Paciente.PacienteEstadisticasDTO;
import com.flowreserve.demo1.model.Request.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {

 @EntityGraph(attributePaths = {"estados"})
 Optional<Request> findById(Long id);

 @Query("SELECT r FROM Request r LEFT JOIN FETCH r.estados WHERE r.id = :id")
 Optional<Request> findByIdWithEstados(@Param("id") Long id);




 Page<Request> findByMedicoId(Long MedicoId, Pageable pageable);
   Page<Request>  findByPacienteId(Long PacienteId, Pageable pageable);
    Page<Request> findByPaciente_IdAndMedico_Id(Long pacienteId, Long medicoId, Pageable pageable);

    @Query("SELECT COUNT(r) FROM Request r WHERE r.medico.id = :medicoId")
    Long countTotalByMedico(@Param("medicoId") Long medicoId);

  //  @Query("SELECT COUNT(r) FROM Request r WHERE r.medico.id = :medicoId AND r.state = 'EN_PROCESO'")
  //  Long countEnCursoByMedico(@Param("medicoId") Long medicoId);

    //@Query("SELECT COUNT(r) FROM Request r WHERE r.medico.id = :medicoId AND r.state = 'COMPLETADA'")
  //  Long countFinalizadasByMedico(@Param("medicoId") Long medicoId);


  //  @Query("SELECT COUNT(r) FROM Request r WHERE r.medico.id = :medicoId AND r.state = 'PENDIENTE'")
    //Long countPendientesByMedico(@Param("medicoId") Long medicoId);

 //   @Query("SELECT COUNT(r) FROM Request r WHERE r.medico.id = :medicoId AND r.state = 'CANCELADA'")
 //   Long countCanceladasByMedico(@Param("medicoId") Long medicoId);

 @Query("""
    SELECT new com.flowreserve.demo1.dto.Medico.MedicoEstadisticasDTO(
        COUNT(DISTINCT r),
        SUM(CASE WHEN er.state = 'EN_PROCESO' THEN 1 ELSE 0 END),
        SUM(CASE WHEN er.state = 'COMPLETADA' THEN 1 ELSE 0 END),
        SUM(CASE WHEN er.state = 'PENDIENTE' THEN 1 ELSE 0 END),
        SUM(CASE WHEN er.state = 'CANCELADA' THEN 1 ELSE 0 END),
        (SELECT COUNT(p) FROM Paciente p WHERE p.medico.id = :medicoId)
    )
    FROM Request r
    JOIN EstadoRequest er ON er.request = r
    WHERE r.medico.id = :medicoId
    AND er.fechaCambio = (
        SELECT MAX(er2.fechaCambio)
        FROM EstadoRequest er2
        WHERE er2.request.id = r.id
    )
""")
 MedicoEstadisticasDTO getEstadisticasByMedico(@Param("medicoId") Long medicoId);

 @Query("""
    SELECT new com.flowreserve.demo1.dto.Paciente.PacienteEstadisticasDTO(
        COUNT(DISTINCT r),
        SUM(CASE WHEN er.state = 'EN_PROCESO' THEN 1 ELSE 0 END),
        SUM(CASE WHEN er.state = 'COMPLETADA' THEN 1 ELSE 0 END),
        SUM(CASE WHEN er.state = 'PENDIENTE' THEN 1 ELSE 0 END),
        SUM(CASE WHEN er.state = 'CANCELADA' THEN 1 ELSE 0 END)
    )
    FROM Request r
    JOIN EstadoRequest er ON er.request = r
    WHERE r.medico.id = :medicoId
      AND r.paciente.id = :pacienteId
      AND er.fechaCambio = (
          SELECT MAX(er2.fechaCambio)
          FROM EstadoRequest er2
          WHERE er2.request.id = r.id
      )
""")
 PacienteEstadisticasDTO getEstadisticasConsultasByPaciente(
         @Param("medicoId") Long medicoId,
         @Param("pacienteId") Long pacienteId
 );


 @Query("""
    SELECT DISTINCT r FROM Request r
    JOIN r.estados e
    WHERE e.fechaCambio = (
        SELECT MAX(e2.fechaCambio) FROM EstadoRequest e2 WHERE e2.request = r
    )
    AND e.state = :estado
""")
 List<Request> findByUltimoEstado(@Param("estado") EstadoSolicitudEnum estado);

}
