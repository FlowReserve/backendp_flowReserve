package com.flowreserve.demo1.service.Response;

import com.flowreserve.demo1.model.Estado.EstadoRequest;
import com.flowreserve.demo1.model.Medico.Medico;
import com.flowreserve.demo1.model.Request.EstadoSolicitudEnum;
import com.flowreserve.demo1.model.Request.Request;
import com.flowreserve.demo1.model.Response.Response;
import com.flowreserve.demo1.repository.Medico.MedicoRepository;
import com.flowreserve.demo1.repository.Request.RequestRepository;
import com.flowreserve.demo1.repository.Response.ResponseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public class ResponseService {
    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Value("${ROOT_PATH}")
    private String rootPath;

    @Transactional
    public void guardarRespuesta(Long requestId, MultipartFile archivoRespuesta) throws IOException {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request no encontrada"));

        String rutaZip = request.getNombreArchivoZip();
        if (rutaZip == null || rutaZip.isBlank()) {
            throw new RuntimeException("No se encontró ruta ZIP del request");
        }

        Path rutaZipPath = Paths.get(rootPath, rutaZip);
        // rutaZip apunta a .../jose/REQ-MBKPTGVN/request/codigoRequest.zip
        Path carpetaRequest = rutaZipPath.getParent();  // .../jose/REQ-MBKPTGVN/request
        if (carpetaRequest == null) {
            throw new RuntimeException("No se pudo determinar la carpeta 'request'");
        }

        Path carpetaCodigo = carpetaRequest.getParent(); // .../jose/REQ-MBKPTGVN
        if (carpetaCodigo == null) {
            throw new RuntimeException("No se pudo determinar la carpeta del código del request");
        }

        // Carpeta "response" al mismo nivel que "request"
        Path carpetaResponse = carpetaCodigo.resolve("response");
        if (!Files.exists(carpetaResponse)) {
            Files.createDirectories(carpetaResponse);
        }

        String nombreArchivo = archivoRespuesta.getOriginalFilename();
        if (nombreArchivo == null || nombreArchivo.isBlank()) {
            throw new RuntimeException("El archivo debe tener un nombre válido");
        }

        Path rutaArchivo = carpetaResponse.resolve(nombreArchivo);
        Files.write(rutaArchivo, archivoRespuesta.getBytes());

        Path rutaRelativa = Paths.get(rootPath).relativize(rutaArchivo);

        Response respuesta = new Response();
        respuesta.setRequest(request);
        respuesta.setNombreArchivoRespuesta(rutaArchivo.toString());
        respuesta.setRutaCompletaArchivo(rutaRelativa.toString());
        respuesta.setFechaCreacion(LocalDateTime.now());

        responseRepository.save(respuesta);
        EstadoRequest nuevoEstado = EstadoRequest.builder()
                .state(EstadoSolicitudEnum.COMPLETADA)
                .comentarios("Respuesta guardada, estado completado")
                .fechaCambio(LocalDateTime.now())
                .request(request)
                .build();

        request.getEstados().add(nuevoEstado);  //con transacccional no hay que guardar 2 veces
        requestRepository.save(request);
    }






    public Resource obtenerArchivoDelMedico(Long requestId) throws IOException {
        Response response = responseRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Respuesta no encontrada"));



        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email= auth.getName();

        Medico medico = medicoRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado"));

        // Validar propiedad
        if (!response.getRequest().getMedico().getId().equals(medico.getId())) {
            throw new AccessDeniedException("No tienes permiso para acceder a este archivo.");
        }

        Path path = Paths.get(rootPath, response.getRutaCompletaArchivo());
        if (!Files.exists(path)) {
            throw new RuntimeException("El archivo no existe en disco");
        }

        return new UrlResource(path.toUri());
    }







}
