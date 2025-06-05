package com.flowreserve.demo1.service;

import com.flowreserve.demo1.model.Request;
import com.flowreserve.demo1.model.Response;
import com.flowreserve.demo1.repository.RequestRepository;
import com.flowreserve.demo1.repository.ResponseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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




    @Transactional
    public void guardarRespuesta(Long requestId, MultipartFile archivoRespuesta) throws IOException {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request no encontrada"));

        String rutaZip = request.getNombreArchivoZip();
        Path carpeta = Paths.get(rutaZip).getParent().resolve("response");

        if (!Files.exists(carpeta)) {
            Files.createDirectories(carpeta);
        }

        // Usar el nombre original del archivo que se sube
        String nombreArchivo = archivoRespuesta.getOriginalFilename();

        // Opcional: validar que no sea nulo o vacío
        if (nombreArchivo == null || nombreArchivo.isBlank()) {
            throw new RuntimeException("El archivo debe tener un nombre válido");
        }

        Path rutaArchivo = carpeta.resolve(nombreArchivo);
        Files.write(rutaArchivo, archivoRespuesta.getBytes());


        // Guardar bytes tal cual llegan
        Files.write(rutaArchivo, archivoRespuesta.getBytes());

        Response respuesta = new Response();
        respuesta.setRequest(request);
        respuesta.setNombreArchivoRespuesta(nombreArchivo);
        respuesta.setRutaCompletaArchivo(rutaArchivo.toString());
        respuesta.setFechaCreacion(LocalDateTime.now());

        responseRepository.save(respuesta);
    }






}
