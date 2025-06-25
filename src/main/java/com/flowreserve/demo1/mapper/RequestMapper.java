package com.flowreserve.demo1.mapper;

import com.flowreserve.demo1.dto.Request.RequestDTO;
import com.flowreserve.demo1.dto.Request.RequestResponseDTO;
import com.flowreserve.demo1.dto.Request.ResponseRequestEstadoUpdateDTO;
import com.flowreserve.demo1.model.Request.EstadoSolicitudEnum;
import com.flowreserve.demo1.model.Request.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@RequiredArgsConstructor
@Component
public class RequestMapper {
    private final PacienteMapper pacienteMapper;
    @Value("${ROOT_PATH}")
    private String rootPath;

    public Request toRequestModel(RequestDTO requestDTO) {
        if (requestDTO == null) return null;
        return Request.builder()
                .presionSistolica(requestDTO.getPresionSistolica())
                .presionDiastolica(requestDTO.getPresionDiastolica())
                .comentarios(requestDTO.getComentarios())
                .estados(new ArrayList<>()) // <--- inicializar aquí
                .build();
    }

    public RequestResponseDTO toRequestResponseDTO(Request request){
        if(request == null) return null;

        // Obtener el estado actual (último estado agregado)
        EstadoSolicitudEnum estadoActual = null;
        if (request.getEstados() != null && !request.getEstados().isEmpty()) {
            estadoActual = request.getEstados()
                    .get(request.getEstados().size() - 1)
                    .getState();
        }

        return RequestResponseDTO.builder()
                .id(request.getId())
                .codigo(request.getCodigo())
                .state(estadoActual)  // usa el estado actual calculado
                .date(request.getDate())
                .presionSistolica(request.getPresionSistolica())
                .presionDiastolica(request.getPresionDiastolica())
                .comentarios(request.getComentarios())
                .nombreArchivoZip(rootPath + request.getNombreArchivoZip())
                .paciente(pacienteMapper.toPacienteResponseDTO(request.getPaciente()))
                .build();
    }


    /**
     * Mapea un request a un DTO personalizado que contiene información básica de una request cuyo estado fue actualizado
     * @param request request que se quiere mapear
     * @return
     */
    public ResponseRequestEstadoUpdateDTO responseRequestEstadoUpdateDTO(Request request){
        if(request == null) return null;
        // Obtener el estado actual (último estado agregado)
        EstadoSolicitudEnum estadoActual = null;
        if (request.getEstados() != null && !request.getEstados().isEmpty()) {
            estadoActual = request.getEstados()
                    .get(request.getEstados().size() - 1)
                    .getState();
        }
        return ResponseRequestEstadoUpdateDTO.builder()
                .id(request.getId())
                .codigo(request.getCodigo())
                .estado(estadoActual)
                .build();
    }

}
