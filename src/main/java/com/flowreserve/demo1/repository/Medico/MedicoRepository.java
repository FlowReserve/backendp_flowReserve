package com.flowreserve.demo1.repository.Medico;
import com.flowreserve.demo1.repository.Request.RequestRepository;
import com.flowreserve.demo1.model.Medico.Medico;
import com.flowreserve.demo1.model.Request.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {


    Optional<Medico> findByEmail(String email);
    Optional<Medico> findById(Long id);

    @Query("SELECT m.id FROM Medico m WHERE m.email = :email")
    Optional<Long> findIdByEmail(String email);

}
