package com.flowreserve.demo1.repository.Medico;
import com.flowreserve.demo1.repository.Request.RequestRepository;
import com.flowreserve.demo1.model.Medico.Medico;
import com.flowreserve.demo1.model.Request.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {


    Optional<Medico> findByEmail(String email);
    Optional<Medico> findById(Long id);


}
