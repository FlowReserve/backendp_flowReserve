package com.flowreserve.demo1.service.Request;

import com.flowreserve.demo1.dto.Request.RequestDTO;
import com.flowreserve.demo1.model.Medico.Medico;
import com.flowreserve.demo1.model.Paciente.Paciente;
import com.flowreserve.demo1.model.Request.Request;
import com.flowreserve.demo1.repository.Medico.MedicoRepository;
import com.flowreserve.demo1.repository.Paciente.PacienteRepository;
import com.flowreserve.demo1.repository.Request.RequestRepository;
import com.flowreserve.demo1.service.Medico.MedicoService;
import com.flowreserve.demo1.service.Paciente.PacienteService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
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

@Service
public class RequestService {

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

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
            Path carpetaDestino = Paths.get("C:/Users/elias.pineiro/Desktop/zips", nombreCarpeta, "request", codigoRequest);
            Files.createDirectories(carpetaDestino);

            // Guardar ZIP con nombre = codigoRequest.zip
            String nombreArchivoZip = codigoRequest + ".zip";
            Path rutaArchivoZip = carpetaDestino.resolve(nombreArchivoZip);
            Files.write(rutaArchivoZip, archivoZip.getBytes());
            request.setNombreArchivoZip(rutaArchivoZip.toAbsolutePath().toString());

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


    public Page<Request> listarRequestsByMedico(Pageable pageable){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email= auth.getName();

        Medico medico = medicoRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado"));

        return requestRepository.findByMedicoId(medico.getId(),pageable);
    }

    public Page <Request> listarRequestByPaciente(Long pacienteId, Pageable pageable){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Medico medico = medicoRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado"));


        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

            if (!paciente.getMedico().getId().equals(medico.getId())) {
                throw new AccessDeniedException("No tienes acceso a las solicitudes de este paciente");

        }

        return requestRepository.findByPaciente_IdAndMedico_Id(paciente.getId(), medico.getId(), pageable);

    }
}
