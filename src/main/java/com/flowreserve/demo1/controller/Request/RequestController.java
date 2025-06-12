package com.flowreserve.demo1.controller.Request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flowreserve.demo1.dto.Request.RequestDTO;
import com.flowreserve.demo1.dto.Request.RequestResponseDTO;
import com.flowreserve.demo1.dto.global.ApiResponseDTO;
import com.flowreserve.demo1.mapper.RequestMapper;
import com.flowreserve.demo1.model.Request.Request;
import com.flowreserve.demo1.service.Request.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private  RequestMapper requestMapper;
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
    //@PreAuthorize("hasAnyRole('DOCTOR')")
    @GetMapping("/mis-solicitudes")
    public  ResponseEntity<Page<RequestResponseDTO>> getMyRequestPatient(@RequestParam long pacienteId, Pageable pageable){
        Page<Request> requests = requestService.listarRequestByPaciente(pacienteId,pageable);

        // Mapear cada Request a RequestResponseDTO
        Page<RequestResponseDTO> dtoPage = requests.map(requestMapper::toRequestResponseDTO);

        return  ResponseEntity.ok(dtoPage);
    }
        //ordenar por medico y estado dto
    //@PreAuthorize("hasAnyRole('ADMIN', 'DEVELOPER')")
        @GetMapping("/listarRequestAdmin")
        public ResponseEntity<ApiResponseDTO<Page<RequestResponseDTO>>> listarTodasLasRequests(
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10") int size,
                @RequestParam(defaultValue = "id") String sortBy,
                @RequestParam(defaultValue = "asc") String sortDir) {
            // Limitar tamaño de página a 25
            int pageSize = Math.min(size, 25);

            Sort sort = sortDir.equalsIgnoreCase("desc")
                    ? Sort.by(sortBy).descending()
                    : Sort.by(sortBy).ascending();

            Pageable pageable = PageRequest.of(page, pageSize, sort);

            Page<Request> requests = requestService.ListarTodasLasRequests(pageable);
            Page<RequestResponseDTO>  pageRequestDTO = requests.map(requestMapper::toRequestResponseDTO);
            return ApiResponseDTO.success("Listado de consultas encontrado", pageRequestDTO, HttpStatus.OK);
        }

//    @GetMapping("/listarRequestAdmin")
//    public ResponseEntity<Page<Request>> listarTodasLasRequests(Pageable pageable) {
//        Page<Request> requests = requestService.ListarTodasLasRequests(pageable);
//        return ResponseEntity.ok(requests);
//    }




}
