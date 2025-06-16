package com.flowreserve.demo1.controller.Request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flowreserve.demo1.dto.Request.EstadoUpdateDTO;
import com.flowreserve.demo1.dto.Request.RequestDTO;
import com.flowreserve.demo1.dto.Request.RequestResponseDTO;
import com.flowreserve.demo1.dto.global.ApiResponseDTO;
import com.flowreserve.demo1.mapper.RequestMapper;
import com.flowreserve.demo1.model.Request.Request;
import com.flowreserve.demo1.service.Request.RequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/solicitudes")

public class RequestController {

    private final RequestService requestService;
    private  final RequestMapper requestMapper;
    private final ObjectMapper objectMapper;

    /**
     * Crea una nueva solicitud de consulta a un paciente en la base de datos.
     * @param requestJson objeto JSON que recibe desde un formData con la información correspondiente a la nueva solicitud que se quiere crear
     * @param archivoZip Multipart que contiene los ficheros adjuntos necesarios para completar la solicitud
     * @return ResponseEntity indicando si la consulta fue exitosa o no.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
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

    /**
     * Funcion para médicos que lista todas las solicitudes existentes en la base de datos asociadas con el usuario logueado.
     * @param pageable objeto de paginacion que devuelve las consultas.
     * @return
     */
    @PreAuthorize("hasAnyRole('MEDICO', 'ADMIN', 'DEVELOPER')")
    @GetMapping("/listarRequestsMedico")
    public ResponseEntity<Page<Request>> getMyRequests(Pageable pageable) {
        Page<Request> requests = requestService.listarRequestsByMedico(pageable);
        return ResponseEntity.ok(requests);
    }


    /**
     * Lista las solicitudes existentes para un paciente con un ID pasado como parámetro
     * @param pacienteId identificador del paciente sobre el que se quiere obtener una consulta específica.
     * @param pageable paginacion con los datos devueltos de las consultas existentes para un paciente bajo un identificador.
     * @return
     */
    @PreAuthorize("hasAnyRole('DOCTOR')")
    @GetMapping("/mis-solicitudes")
    public  ResponseEntity<Page<RequestResponseDTO>> getMyRequestPatient(@RequestParam long pacienteId, Pageable pageable){
        Page<Request> requests = requestService.listarRequestByPaciente(pacienteId,pageable);

        // Mapear cada Request a RequestResponseDTO
        Page<RequestResponseDTO> dtoPage = requests.map(requestMapper::toRequestResponseDTO);

        return  ResponseEntity.ok(dtoPage);
    }

    /**
     * Lista las consultas totales existentes en la base de datos
     * @param page número de pagina
     * @param size tamaño de la página
     * @param sortBy atributo de ordenación
     * @param sortDir dirección de ordenación
     * @return objeto paginado con la información de la consulta solicitada.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'DEVELOPER')")
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


    /**
     * Funcion que permite actualizar el estado de una consulta por parte de un usuario administrador.
      * @param id identificador de la consulta sobre la que se quiere actualizar su estado.
     * @param estadoUpdateDTO estado nuevo que se le quiere proporcionar a la solicitud
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'DEVELOPER')")
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstadoRequest(
            @PathVariable Long id,
            @Valid @RequestBody EstadoUpdateDTO estadoUpdateDTO) {

        requestService.cambiarEstado(id, estadoUpdateDTO.getEstado());
        return ResponseEntity.ok("Estado actualizado correctamente.");
    }


    //@PreAuthorize("hasAnyRole('ADMIN', 'DEVELOPER')")
    @GetMapping("/{id}/archivo")
    public ResponseEntity<?> procesarArchivos(@PathVariable Long id) {

        try {
            List<String> archivosProcesados = requestService.obtenerZipCompleto(id);
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Archivos procesados correctamente");
            respuesta.put("archivos", archivosProcesados);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al procesar archivos"));
        }


    }
}
