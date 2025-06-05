package com.flowreserve.demo1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flowreserve.demo1.dto.PacienteDTO;
import com.flowreserve.demo1.dto.RequestDTO;
import com.flowreserve.demo1.model.Medico;
import com.flowreserve.demo1.model.Request;
import com.flowreserve.demo1.repository.RequestRepository;
import com.flowreserve.demo1.service.RequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.flowreserve.demo1.service.MedicoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController

@RequestMapping("/requests")

public class RequestController


{


    @Autowired
    private RequestService requestService;


    @PostMapping(value = "/crear", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> crearRequestConArchivos(
            @RequestPart("json") RequestDTO requestDTO,
            @RequestPart("archivoZip") MultipartFile archivoZip) {
        try {
            String codigo = requestService.crearRequestConArchivos(requestDTO, archivoZip);
            return ResponseEntity.ok("Request creada con código: " + codigo);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la request: " + e.getMessage());
        }
    }


}
