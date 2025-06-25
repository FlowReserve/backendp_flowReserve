package com.flowreserve.demo1.mapper;

import com.flowreserve.demo1.dto.EstadoRequest.EstadoRequestDTO;
import com.flowreserve.demo1.dto.Request.RequestDTO;
import com.flowreserve.demo1.dto.Request.RequestResponseDTO;
import com.flowreserve.demo1.dto.Request.ResponseRequestEstadoUpdateDTO;
import com.flowreserve.demo1.model.Estado.EstadoRequest;
import com.flowreserve.demo1.model.Request.EstadoSolicitudEnum;
import com.flowreserve.demo1.model.Request.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class RequestMapper {
    private final PacienteMapper pacienteMapper;
    private final EstadoRequestMapper estadoRequestMapper;
    private String rutaPublica;


    @Value("${ROOT_PATH}")
    private String rootPath;

    @Value("${public.base-url}")
    private String publicBaseUrl;


    public Request toRequestModel(RequestDTO requestDTO) {
        if (requestDTO == null) return null;
        return Request.builder()
                .presionSistolica(requestDTO.getPresionSistolica())
                .presionDiastolica(requestDTO.getPresionDiastolica())
                .comentarios(requestDTO.getComentarios())
                .estados(new HashSet<>()) // <--- inicializar aquí
                .lesiones(requestDTO.getLesiones())
                .lesionesPersonalizadas(requestDTO.getLesionesPersonalizadas())
                .build();
    }

    public RequestResponseDTO toRequestResponseDTO(Request request){
        if(request == null) return null;

        Set<EstadoRequestDTO>  setEstados = new HashSet<>();
        if (!request.getEstados().isEmpty()) {
             setEstados = request.getEstados().stream()
                    .map(estadoRequestMapper::estadoRequestDTO)
                    .collect(Collectors.toSet());

        }
        String nombreArchivoZip = request.getNombreArchivoZip().replace("\\", "/");
        int lastSlashIndex = nombreArchivoZip.lastIndexOf('/');

        String carpetaContenedora = (lastSlashIndex > 0) ? nombreArchivoZip.substring(0, lastSlashIndex + 1) : "";
// "REQ-MC8ZTAJN_1234/"
        String rutaPublica =publicBaseUrl + carpetaContenedora ;


        return RequestResponseDTO.builder()
                .id(request.getId())
                .codigo(request.getCodigo())
                .state(request.getState())
                .date(request.getDate())
                .presionSistolica(request.getPresionSistolica())
                .presionDiastolica(request.getPresionDiastolica())
                .listadoEstados(setEstados)
                .comentarios(request.getComentarios())
                .nombreArchivoZip(request.getNombreArchivoZip())
                .lesiones(request.getLesiones())
                .lesionesPersonalizadas(request.getLesionesPersonalizadas())
                .rutaPublica(rutaPublica)
                .nombreArchivoZip( request.getNombreArchivoZip().replace("\\", "/"))
                .paciente(pacienteMapper.toPacienteResponseDTO(request.getPaciente()))
                .build();
    }


    /**
     * Mapea un request a un DTO personalizado que contiene información básica de una request cuyo estado fue actualizado
     * @param request request que se quiere mapear
     * @return
     */
    public ResponseRequestEstadoUpdateDTO responseRequestEstadoUpdateDTO(Request request) {
        if (request == null) return null;

        // Obtener el estado actual (el de fechaCambio más reciente)
        EstadoSolicitudEnum estadoActual = null;
        if (request.getEstados() != null && !request.getEstados().isEmpty()) {
            estadoActual = request.getEstados().stream()
                    .max(Comparator.comparing(EstadoRequest::getFechaCambio)) // ordenar por fecha
                    .map(EstadoRequest::getState)
                    .orElse(null);
        }
        return ResponseRequestEstadoUpdateDTO.builder()
                .id(request.getId())
                .codigo(request.getCodigo())
                .estado(estadoActual)
                .build();
    }

}
