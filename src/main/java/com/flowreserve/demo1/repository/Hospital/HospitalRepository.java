package com.flowreserve.demo1.repository.Hospital;

import com.flowreserve.demo1.model.Hospital.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    Optional<Hospital> findByCodigo(Long codigo);


}
