package com.flowreserve.demo1.repository.Response;

import com.flowreserve.demo1.model.Response.Response;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ResponseRepository extends JpaRepository<Response, Long> {
    List<Response> findByRequestId(Long requestId);


}
