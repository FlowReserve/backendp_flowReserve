package com.flowreserve.demo1.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flowreserve.demo1.dto.RequestDTO;
import com.flowreserve.demo1.model.Medico;
import com.flowreserve.demo1.model.Paciente;
import com.flowreserve.demo1.model.Request;
import com.flowreserve.demo1.repository.HospitalRepository;
import com.flowreserve.demo1.repository.RequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RequestService {

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private RequestRepository requestRepository;

    @Transactional
    public String crearRequestConArchivos(RequestDTO dto, MultipartFile archivoZip) throws IOException {
        Request request = new Request();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailMedico = auth.getName();

        Medico medico = medicoService.findByEmail(emailMedico);
        request.setMedico(medico);

        Paciente paciente = pacienteService.findById(dto.getIdPaciente());
        request.setPaciente(paciente);

        // Fecha y estado
        request.setDate(LocalDateTime.now());
        request.setState("PENDIENTE");
        request.setPressureA(dto.getPressureA());
        request.setPressureB(dto.getPressureB());
        request.setCampoComentarios(dto.getComentarios());

        // Guardar el request para generar el código (si es generado en la BD)
        if (request.getCodigo() == null) {
            requestRepository.save(request);
        }

        // Si hay archivo ZIP
        if (!archivoZip.isEmpty()) {
            String nombreCarpeta = medico.getNombre().replaceAll("\\s+", "_");
            String codigoRequest = request.getCodigo();

            // Carpeta: NombreMedico/CodigoRequest/
            Path carpetaDestino = Paths.get("C:/Users/elias.pineiro/Desktop/zips", nombreCarpeta, codigoRequest);
            Files.createDirectories(carpetaDestino);

            // Guardar ZIP con nombre = codigoRequest.zip
            String nombreArchivoZip = codigoRequest + ".zip";
            Path rutaArchivoZip = carpetaDestino.resolve(nombreArchivoZip);
            Files.write(rutaArchivoZip, archivoZip.getBytes());
            request.setNombreArchivoZip(nombreArchivoZip);

            // Crear archivo .txt con presiones y comentarios
            String contenidoTxt = "Presión A: " + dto.getPressureA() + "\n"
                    + "Presión B: " + dto.getPressureB() + "\n"
                    + "Comentarios: " + dto.getComentarios();

            String nombreArchivoTxt = "info_" + codigoRequest + ".txt";
            Path rutaArchivoTxt = carpetaDestino.resolve(nombreArchivoTxt);
            Files.writeString(rutaArchivoTxt, contenidoTxt, StandardCharsets.UTF_8);
        }

        // Guardar request actualizado
        requestRepository.save(request);
        return request.getCodigo();
    }



}
