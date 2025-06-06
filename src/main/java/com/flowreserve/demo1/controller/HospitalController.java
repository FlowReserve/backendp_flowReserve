package com.flowreserve.demo1.controller;

import com.flowreserve.demo1.dto.HospitalDTO;
import com.flowreserve.demo1.repository.HospitalRepository;

import com.flowreserve.demo1.model.Hospital;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


//@Controller
@RestController
@RequestMapping("/api/v1/hospital")
public class HospitalController {
    @Autowired
    private HospitalRepository hospitalRepository;


  //  @PreAuthorize("hasRole('ADMIN_HOSPITAL')")

    @PostMapping
    public ResponseEntity<?> crearHospital(@RequestBody HospitalDTO hospitalDTO) {
        try {

            Hospital hospital = new Hospital();

            hospital.setNombre(hospitalDTO.getNombreHospital());

            hospital.setCodigo(hospitalDTO.getCodigoHospital());


            Hospital guardado = hospitalRepository.save(hospital);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(guardado); // Devuelve el hospital guardado como JSON
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el hospital: " + e.getMessage());
        }
    }

}