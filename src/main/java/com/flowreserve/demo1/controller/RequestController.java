package com.flowreserve.demo1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flowreserve.demo1.dto.RequestDTO;
import com.flowreserve.demo1.model.Medico;
import com.flowreserve.demo1.model.Request;
import com.flowreserve.demo1.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

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
import java.util.UUID;

@RestController
@RequestMapping("/requests")

public class RequestController
{

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private MedicoService medicoService;
    private final String UPLOAD_DIR = "uploads/requests/";
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> crearRequestConArchivos(
      //     @RequestPart("json") MultipartFile json,
            @RequestPart("archivoZip") MultipartFile archivoZip) throws IOException {

        // Parsear el JSON recibido a DTO
      //      ObjectMapper mapper = new ObjectMapper();
    //       RequestDTO dto = mapper.readValue(json.getBytes(), RequestDTO.class);

        // Crear entidad Request
               Request request = new Request();




        if (!archivoZip.isEmpty()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String nombreMedico = auth.getName(); // aquí puede ser el email

            // Opcional: obtener el Medico si necesitas el nombre completo
            Medico medico = medicoService.findByEmail(nombreMedico);
            String nombreCarpeta = medico.getNombre().replaceAll("\\s+", "_"); // evita espacios

            // Define la ruta base del ZIP
            Path carpetaDestino = Paths.get("C:/Users/elias.pineiro/Desktop/zips", nombreCarpeta);
            Files.createDirectories(carpetaDestino); // crea carpeta si no existe

            String nombreArchivo = UUID.randomUUID() + "_" + archivoZip.getOriginalFilename();
            Path rutaArchivo = carpetaDestino.resolve(nombreArchivo);
            Files.write(rutaArchivo, archivoZip.getBytes());
            request.setUser(medico);  // <--- aquí asocias el médico
            request.setDate(LocalDateTime.now());  // o LocalDate si solo necesitas fecha
            request.setState("PENDIENTE");
            request.setNombreArchivoZip(nombreArchivo);
        //          request.setPressureA(dto.getPressureA());
      //            request.setPressureB(dto.getPressureB());

        }

        requestRepository.save(request);

        return ResponseEntity.ok("Request con ZIP creada con éxito.");
    }

}
