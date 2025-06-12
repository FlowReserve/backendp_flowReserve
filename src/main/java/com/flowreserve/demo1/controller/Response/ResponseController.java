package com.flowreserve.demo1.controller.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flowreserve.demo1.dto.Response.ResponseDTO;
import com.flowreserve.demo1.dto.global.ApiResponseDTO;
import com.flowreserve.demo1.dto.global.ArchivoInfoDTO;
import com.flowreserve.demo1.service.Response.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/responses")
public class ResponseController {

    private final ResponseService responseService;

    public ResponseController(ResponseService responseService) {
        this.responseService = responseService;
    }
    @Autowired
    private ObjectMapper objectMapper;
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> subirArchivoRespuesta(
            @RequestPart("json") String json,
            @RequestPart("archivoRespuesta") MultipartFile archivo) {
        try {
            ResponseDTO dto = objectMapper.readValue(json, ResponseDTO.class);
            responseService.guardarRespuesta(dto.getRequestId(), archivo);
            return ResponseEntity.ok("Archivo guardado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar archivo: " + e.getMessage());
        }
    }

    @GetMapping("/medico/info/{requestId}")
    public ResponseEntity<ApiResponseDTO<ArchivoInfoDTO>> obtenerInfoArchivo(@PathVariable Long requestId) {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Resource archivo = responseService.obtenerArchivoDelMedico(requestId);

            ArchivoInfoDTO archivoInfo = ArchivoInfoDTO.builder()
                    .id(requestId)
                    .filename(archivo.getFilename())
                    .contentType("application/pdf")
                    .size(archivo.contentLength())
                    .downloadUrl("/api/v1/responses/medico/descargar/" + requestId)
                    .fechaCreacion(Instant.now()) // O la fecha real del archivo
                    .build();

            return ApiResponseDTO.success(
                    "Información del archivo obtenida correctamente",
                    archivoInfo,
                    HttpStatus.OK
            );

        } catch (Exception e) {
            return ApiResponseDTO.error(
                    "Error interno del servidor",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }



    @GetMapping("/medico/descargar/{requestId}")
    public ResponseEntity<?> descargarArchivo(@PathVariable Long requestId) {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Resource archivo = responseService.obtenerArchivoDelMedico(requestId);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + archivo.getFilename() + "\"")
                    .body(archivo);

        }  catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "Error interno del servidor");
            error.put("error", "Error técnico");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(error);
        }
    }


}
