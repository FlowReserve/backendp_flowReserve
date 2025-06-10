package com.flowreserve.demo1.controller.Request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flowreserve.demo1.dto.Request.RequestDTO;
import com.flowreserve.demo1.model.Request.Request;
import com.flowreserve.demo1.service.Request.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/api/v1/solicitudes")

public class RequestController


{


    @Autowired
    private RequestService requestService;


    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(value = "/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> crearRequestConArchivos(
            @RequestPart("json") String requestJson,
            @RequestPart("archivoZip") MultipartFile archivoZip) {
        try {
            RequestDTO requestDTO = objectMapper.readValue(requestJson, RequestDTO.class);
            String codigo = requestService.crearRequestConArchivos(requestDTO, archivoZip);
            return ResponseEntity.ok("Request creada con código: " + codigo);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la request: " + e.getMessage());
        }
    }
  //admin
    @GetMapping("/listarRequestsMedico")
    public ResponseEntity<Page<Request>> getMyRequests(Pageable pageable) {
        Page<Request> requests = requestService.listarRequestsByMedico(pageable);
        return ResponseEntity.ok(requests);
    }
    @GetMapping("/mis-solicitudes")
    public  ResponseEntity<Page<Request>> getMyRequestPatient(@RequestParam long pacienteId,Pageable pageable){
        Page<Request> requests = requestService.listarRequestByPaciente(pacienteId,pageable);
        return  ResponseEntity.ok(requests);
    }
        //ordenar por medico y estado dto
    //@PreAuthorize("hasAnyRole('ADMIN', 'DEVELOPER')")
    @GetMapping("/listarRequestAdmin")
    public ResponseEntity<Page<Request>> listarTodasLasRequests(Pageable pageable) {
        Page<Request> requests = requestService.ListarTodasLasRequests(pageable);
        return ResponseEntity.ok(requests);
    }




}
