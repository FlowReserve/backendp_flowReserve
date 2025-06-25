package com.flowreserve.demo1.service.Request;

import com.flowreserve.demo1.dto.EstadoRequest.EstadoRequestDTO;
import com.flowreserve.demo1.dto.Medico.MedicoEstadisticasDTO;
import com.flowreserve.demo1.dto.Request.RequestDTO;
import com.flowreserve.demo1.dto.Request.RequestResponseDTO;
import com.flowreserve.demo1.exceptions.CustomExceptions;
import com.flowreserve.demo1.mapper.EstadoRequestMapper;
import com.flowreserve.demo1.mapper.RequestMapper;
import com.flowreserve.demo1.model.Estado.EstadoRequest;
import com.flowreserve.demo1.model.Medico.Medico;
import com.flowreserve.demo1.model.Paciente.Paciente;
import com.flowreserve.demo1.model.Request.EstadoSolicitudEnum;
import com.flowreserve.demo1.model.Request.Request;
import com.flowreserve.demo1.repository.EstadoRequest.EstadoRequestRepository;
import com.flowreserve.demo1.repository.Hospital.HospitalRepository;
import com.flowreserve.demo1.repository.Medico.MedicoRepository;
import com.flowreserve.demo1.repository.Paciente.PacienteRepository;
import com.flowreserve.demo1.repository.Request.RequestRepository;
import com.flowreserve.demo1.service.Medico.MedicoService;
import com.flowreserve.demo1.service.Paciente.PacienteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@RequiredArgsConstructor

@Service
public class RequestService {


    private final MedicoService medicoService;
    private final PacienteService pacienteService;
    private final RequestRepository requestRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final RequestMapper requestMapper;
    private final HospitalRepository hospitalRepository;
    private final EstadoRequestMapper estadoRequestMapper;

    @Value("${ROOT_PATH}")
    private String rootPath;

    @Value("${PRODUCCION_PATH}")
    private String produccionPath;

    @Transactional
    public Request crearRequestConArchivos(RequestDTO dto, MultipartFile archivoZip) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailMedico = auth.getName();
        Request request = requestMapper.toRequestModel(dto);
        Medico medico = medicoService.findByEmail(emailMedico);


// Crear estado inicial
        EstadoRequestDTO estadoDTO = new EstadoRequestDTO();
        estadoDTO.setEstado(EstadoSolicitudEnum.PENDIENTE);
        estadoDTO.setComentarios("Solicitud creada automáticamente");

        EstadoRequest estadoInicial = estadoRequestMapper.toEstadoRequestModel(estadoDTO);
        estadoInicial.setFechaCambio(LocalDateTime.now());
        estadoInicial.setRequest(request);

// Agregar estado a la lista
        request.getEstados().add(estadoInicial);
        request.setMedico(medico);

        Paciente paciente = pacienteService.findById(dto.getIdPaciente());
        request.setPaciente(paciente);

        // Fecha y estado
        request.setDate(LocalDateTime.now());
         request.setState(EstadoSolicitudEnum.PENDIENTE);


        String codigoHospital = medico.getHospital().getCodigo();// ej: "CHUS"
        String codigoGenerado = "REQ-" + Long.toString(System.currentTimeMillis(), 36).toUpperCase() + "_" + codigoHospital;
        request.setCodigo(codigoGenerado);
        String codigoRequest = request.getCodigo();
        // Si hay archivo ZIP
        if (!archivoZip.isEmpty()) {

            String nombreCarpeta = codigoRequest;


            Path carpetaDestino = Paths.get(rootPath, nombreCarpeta, "request");
            Files.createDirectories(carpetaDestino);

            // Guardar ZIP con nombre = codigoRequest.zip
            String nombreArchivoZip = codigoRequest + ".zip";
            Path rutaArchivoZip = carpetaDestino.resolve(nombreArchivoZip);
            Files.copy(archivoZip.getInputStream(), rutaArchivoZip, StandardCopyOption.REPLACE_EXISTING);

            // ✅ Descomprimir
            //    descomprimirZip(rutaArchivoZip, carpetaDestino);


            // Guardar ruta relativa en la BD
            Path rutaRelativa = Paths.get(nombreCarpeta, nombreArchivoZip);
            request.setNombreArchivoZip(rutaRelativa.toString());

            // Crear archivo .txt con presiones y comentarios
            String contenidoTxt = "Presión Sistólica: " + dto.getPresionSistolica() + "\n"
                    + "Presión diastólica: " + dto.getPresionDiastolica() + "\n"
                    + "Comentarios: " + dto.getComentarios() + "\n"
                    + "Lesiones: " + dto.getLesiones() + "\n"
                    + "Lesiones_personalizadas: " + dto.getLesionesPersonalizadas();

            String nombreArchivoTxt = "txt_" + nombreCarpeta + ".txt";
            Path rutaArchivoTxt = carpetaDestino.resolve(nombreArchivoTxt);
            Files.writeString(rutaArchivoTxt, contenidoTxt, StandardCharsets.UTF_8);


            if (produccionPath != null && !produccionPath.isBlank()) {
                Path carpetaProduccion = Paths.get(produccionPath, nombreCarpeta, "request");
                Files.createDirectories(carpetaProduccion);

                // Copiar ZIP
                Path destinoZip = carpetaProduccion.resolve(rutaArchivoZip.getFileName());
                Files.copy(rutaArchivoZip, destinoZip, StandardCopyOption.REPLACE_EXISTING);

                // Copiar TXT
                Path destinoTxt = carpetaProduccion.resolve(rutaArchivoTxt.getFileName());
                Files.copy(rutaArchivoTxt, destinoTxt, StandardCopyOption.REPLACE_EXISTING);

                System.out.println("ZIP y TXT copiados a producción:");
                System.out.println("ZIP -> " + destinoZip.toString());
                System.out.println("TXT -> " + destinoTxt.toString());
                descomprimirZip(destinoZip, carpetaProduccion);
            }

        }

        // Guardar y devolver el request actualizado
        return  requestRepository.save(request);
    }

    public void descomprimirZip(Path zipPath, Path destino) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(zipPath))) {
            ZipEntry entry;
            byte[] buffer = new byte[4096];  // Buffer para lectura segura

            while ((entry = zis.getNextEntry()) != null) {
                Path archivoDestino = destino.resolve(entry.getName()).normalize();

                // ✅ Seguridad: evitar que escriban fuera de la carpeta destino
                if (!archivoDestino.startsWith(destino)) {
                    throw new IOException("Entrada ZIP no válida: " + entry.getName());
                }

                if (entry.isDirectory()) {
                    Files.createDirectories(archivoDestino);
                } else {
                    Files.createDirectories(archivoDestino.getParent());
                    try (OutputStream fos = Files.newOutputStream(archivoDestino)) {
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                }

                zis.closeEntry();
            }
        }
    }


    // faltara meter carpeta asociada previo mandar los 2 archivos a response
    public List<String> obtenerZipCompleto(Long requestId) throws IOException {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new FileNotFoundException("Request no encontrado"));

        String rutaZipRelativa = request.getNombreArchivoZip();
        if (rutaZipRelativa == null || rutaZipRelativa.isEmpty()) {
            throw new FileNotFoundException("No hay archivo ZIP asociado a este request");
        }

        Path rutaZipOriginal = Paths.get(rootPath).resolve(rutaZipRelativa);
        if (!Files.exists(rutaZipOriginal)) throw new FileNotFoundException("ZIP original no encontrado");

        String codigoRequest = request.getCodigo();
        Path carpetaRequest = rutaZipOriginal.getParent();
        Path rutaTxt = carpetaRequest.resolve("info_" + codigoRequest + ".txt");
        if (!Files.exists(rutaTxt)) throw new FileNotFoundException("TXT no encontrado");

        // === Copiar archivos a carpeta 'response'
        Path carpetaResponse = carpetaRequest.getParent().resolve("response");
        Files.createDirectories(carpetaResponse);

        Path destinoZip = carpetaResponse.resolve(rutaZipOriginal.getFileName());
        Path destinoTxt = carpetaResponse.resolve(rutaTxt.getFileName());

        Files.copy(rutaZipOriginal, destinoZip, StandardCopyOption.REPLACE_EXISTING);
        Files.copy(rutaTxt, destinoTxt, StandardCopyOption.REPLACE_EXISTING);

        // === Crear nuevo ZIP para descarga
        Path zipFinal = Files.createTempFile("request-" + codigoRequest + "-", ".zip");

        try (ZipOutputStream zipOut = new ZipOutputStream(Files.newOutputStream(zipFinal))) {
            agregarArchivoAlZip(destinoZip, destinoZip.getFileName().toString(), zipOut);
            agregarArchivoAlZip(destinoTxt, destinoTxt.getFileName().toString(), zipOut);
        }

        return List.of(rutaZipOriginal.getFileName().toString(), rutaTxt.getFileName().toString());
    }



    private void agregarArchivoAlZip(Path archivo, String nombreEnZip, ZipOutputStream zipOut) throws IOException {
        try (InputStream in = Files.newInputStream(archivo)) {
            ZipEntry zipEntry = new ZipEntry(nombreEnZip);
            zipOut.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) > 0) {
                zipOut.write(buffer, 0, len);
            }
            zipOut.closeEntry();
        }
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


    public Page<Request> ListarTodasLasRequests(Pageable pageable) {
        return requestRepository.findAll(pageable);
    }


    /**
     * Actualiza el estado de una consulta.
     *
     * @param requestID   identificador de la consulta sobre la que se quiere modificar el estado
     * @param nuevoEstado nuevo estado que se le quiere añadir a la consulta.
     * @Return Request actualizado con el nuevo estado.
     */
    @Transactional
    public Request cambiarEstado(Long requestID, EstadoSolicitudEnum nuevoEstado) {

        Request request = requestRepository.findById(requestID)
                .orElseThrow(() -> new RuntimeException("Request no encontrada"));

        if (request.getState() == EstadoSolicitudEnum.COMPLETADA) {
            throw new CustomExceptions.UnmodifiableRequestException("No se puede cambiar una solicitud completada.");
        }

        // Crear nuevo EstadoRequest
        EstadoRequest nuevoEstadoRequest = EstadoRequest.builder().state(nuevoEstado).fechaCambio(LocalDateTime.now()).comentarios("Cambio de estado manual") // puedes parametrizar si quieres
                .request(request).build();

        // ✅ Agregar el nuevo estado
        request.getEstados().add(nuevoEstadoRequest);

        request.setState(nuevoEstado);
        requestRepository.save(request);
        return request;
    }

    /**
     * Obtiene las estadisticas de un medico bajo una unica consulta SQL
     * @param medicoId identificador del medico sobre el que se quiere realizar la consulta.
     * @return
     */
    public MedicoEstadisticasDTO obtenerResumenConsultasPorMedicoOptimized(Long medicoId) {
        return requestRepository.getEstadisticasByMedico(medicoId);
    }


    public List<Request> obtenerRequestsPorUltimoEstado(EstadoSolicitudEnum estado) {
        return requestRepository.findByUltimoEstado(estado);
    }

}
