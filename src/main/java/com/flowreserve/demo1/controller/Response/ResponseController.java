package com.flowreserve.demo1.controller.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flowreserve.demo1.dto.Response.ResponseDTO;
import com.flowreserve.demo1.service.Response.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/responses")
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

}
