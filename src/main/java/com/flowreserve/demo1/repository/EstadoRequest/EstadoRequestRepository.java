package com.flowreserve.demo1.repository.EstadoRequest;

import com.flowreserve.demo1.model.Estado.EstadoRequestMedico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoRequestRepository extends JpaRepository<EstadoRequestMedico, Long> {


}
