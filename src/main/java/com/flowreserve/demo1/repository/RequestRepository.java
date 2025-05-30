package com.flowreserve.demo1.repository;

import com.flowreserve.demo1.model.Report;
import com.flowreserve.demo1.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {

}
