package com.flowreserve.demo1.controller.Response;

import com.flowreserve.demo1.dto.global.ApiResponseDTO;
import com.flowreserve.demo1.dto.global.ArchivoInfoDTO;
import com.flowreserve.demo1.service.Response.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/responses")
public class ResponseController {

    private final ResponseService responseService;

    /**
     * Carga un documento PDF a la consulta realizada por un médico sobre un paciente.
     * @param requestId identificador de la consulta sobre la que se quiere realizar la operación de subida de PDF.
     * @param archivo Archivo que se quiere subir en la BBDD relacionado con la consulta
     * @return Respuesta ApiResponseDTO indicando si la consulta realizada ha sido exitosa o no.
     */
    @PostMapping(value = "/upload/{requestId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseDTO<Object>> subirArchivoRespuesta(
            @PathVariable Long requestId,
            @RequestPart("archivoRespuesta") MultipartFile archivo) {
        try {
            if (archivo == null || archivo.isEmpty()) {
                return ApiResponseDTO.error("El archivo no fue proporcionado o está vacío", HttpStatus.BAD_REQUEST);
            }

            responseService.guardarRespuesta(requestId, archivo);

            return ApiResponseDTO.success("Archivo guardado correctamente", null, HttpStatus.OK);
        } catch (Exception e) {
            return ApiResponseDTO.error("Error interno: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Funcion que sirve para obtener información detallada previa sobre un fichero que se quiere descargar: tamaño, fecha creacion, tipo...
     * @param requestId identificador de la consulta sobre la que se quiere obtener información de los documentos existentes.
     * @return ApiResponseDTO<ArchivoInfoDTO> con la información solicitada.
     */
    @GetMapping("/medico/info/{requestId}")
    public ResponseEntity<ApiResponseDTO<ArchivoInfoDTO>> obtenerInfoArchivo(@PathVariable Long requestId) {
        try {

            Resource archivo = responseService.obtenerArchivoDelMedico(requestId);

            ArchivoInfoDTO archivoInfo = ArchivoInfoDTO.builder()
                    .filename(archivo.getFilename())
                    .contentType("application/pdf")
                    .size(archivo.contentLength())
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


    /**
     * Controller que se encarga de descargar un documento relacionado con una consulta. Para que la descarga se complete,
     * el usuario logueado debe ser el mismo que el usuario que hizo la consulta.
     * @param requestId identificador de la consulta sobre la que se quiere descargar el fichero.
     * @return
     */
    @GetMapping("/medico/descargar/{requestId}")
    public ResponseEntity<?> descargarArchivo(@PathVariable Long requestId) {
        try {
            //Descarga el archivo de un paciente y valida que quien está realizando la consulta sea el médico asociado con dicha consulta.
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
