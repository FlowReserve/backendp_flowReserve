package com.flowreserve.demo1.controller.Paciente;
import com.flowreserve.demo1.dto.Paciente.PacienteDTO;
import com.flowreserve.demo1.dto.Paciente.PacienteEstadisticasDTO;
import com.flowreserve.demo1.dto.Paciente.PacienteResponseDTO;
import com.flowreserve.demo1.dto.global.ApiResponseDTO;
import com.flowreserve.demo1.mapper.PacienteMapper;
import com.flowreserve.demo1.model.Paciente.Paciente;
import com.flowreserve.demo1.service.Medico.ObtenerMedicoService;
import com.flowreserve.demo1.service.Paciente.PacienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api/v1/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;
    private final ObtenerMedicoService obtenerMedicoService;
    private final PacienteMapper pacienteMapper;


    /**
     * Controller que se encarga de crear un paciente en la base de datos y devuelve un DTO con la información del nuevo paciente creado.
     * @param pacienteDTO datos del paciente que se quiere subir en la base de datos.
     * @return ApiResponse con los datos del paciente subido.
     */
    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/new")
    public ResponseEntity<ApiResponseDTO<PacienteResponseDTO>> crearPaciente(@Valid @RequestBody PacienteDTO pacienteDTO) {
        Paciente paciente = pacienteService.crearPaciente(pacienteDTO);
        PacienteResponseDTO pacienteResponseDTO = pacienteMapper.toPacienteResponseDTO(paciente);

        return ApiResponseDTO.success("Paciente creado con éxito", pacienteResponseDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/mis-pacientes")
    public ResponseEntity<ApiResponseDTO<Page<PacienteResponseDTO>>> listarPacientesDelMedicoAutenticado(
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
        Page<Paciente> pacientes = pacienteService.obtenerPacientesPorMedicoAutenticado(pageable);
        Page<PacienteResponseDTO> pacienteDTOs = pacientes.map(pacienteMapper::toPacienteResponseDTO);
        return ApiResponseDTO.success("Listado de pacientes encontrado con éxito", pacienteDTOs, HttpStatus.OK);
    }

    /**
     * Endpoint que sobre un idPaciente realiza una busqueda comprobando:
     * 1- El Medico que realiza la consulta sea el medico asociado con el paciente
     * 2- El paciente sobre el que se realiza la consulta exista realmente.
     * @param idPaciente identificador del paciente sobre el que se quiere realizar la búsqueda
     * @return APIResponse con la información del paciente sobre el que se ha realizado la consulta.
     */
    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<PacienteResponseDTO>> obtenerPacienteDeMedicoAutenticado(@PathVariable("id") Long idPaciente){
        //Comprueba datos del usuario authenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long idMedico = obtenerMedicoService.obtenerIdMedicoPorMail(auth.getName());
        //Comprueba que el paciente está asociado con el medico authenticado
        Paciente paciente = pacienteService.findPacienteByIdAndMedicoId(idPaciente, idMedico);
        PacienteResponseDTO pacienteResponseDTO = pacienteMapper.toPacienteResponseDTO(paciente);

        return ApiResponseDTO.success("Paciente encontrado con éxito",pacienteResponseDTO, HttpStatus.OK);
    }

    /**
     * Obtiene un resumen de las estadisticas de consultas de un paciente, bajo el identificador del usuario logueado.
     * @param idPaciente identificador del paciente sobre el que se quieren obtener las estadisticas
     * @return ApiResponseDTO con la información de las estadísticas de un paciente.
     */
    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/{id}/resumen")
    public ResponseEntity<ApiResponseDTO<PacienteEstadisticasDTO>> obtenerEstadisticasPaciente(@PathVariable("id") Long idPaciente){
        //Comprueba datos del usuario authenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long idMedico = obtenerMedicoService.obtenerIdMedicoPorMail(auth.getName());
        PacienteEstadisticasDTO pacienteEstadisticasDTO = pacienteService.obtenerEstadisticasConsultasByPacienteID(idMedico, idPaciente);
        return ApiResponseDTO.success("Estadísticas del paciente encontradas con éxito", pacienteEstadisticasDTO, HttpStatus.OK);
    }

    @GetMapping("/count-by-medico/{medicoId}")
    public ResponseEntity<Map<String, Object>> contarPacientes(@PathVariable Long medicoId) {
        Long total = pacienteService.obtenerNumeroDePacientesDelMedico(medicoId);
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("medicoId", medicoId);
        respuesta.put("totalPacientes", total);
        return ResponseEntity.ok(respuesta);
    }

}
