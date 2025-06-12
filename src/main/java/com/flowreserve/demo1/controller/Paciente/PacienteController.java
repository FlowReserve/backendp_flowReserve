package com.flowreserve.demo1.controller.Paciente;
import com.flowreserve.demo1.dto.Paciente.PacienteDTO;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;
    private final ObtenerMedicoService obtenerMedicoService;
    private final PacienteMapper pacienteMapper;


    //devolver dto y no un ?
//@PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/new")
    public ResponseEntity<?> crearPaciente(@Valid @RequestBody PacienteDTO pacienteDTO) {
        pacienteService.crearPaciente(pacienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("mensaje", "Paciente creado correctamente"));
    }

    //@PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/mis-pacientes")
    public ResponseEntity<ApiResponseDTO<Page<Paciente>>> listarPacientesDelMedicoAutenticado(
            @PageableDefault(size = 10, sort = "apellido") Pageable pageable) {

        Page<Paciente> pacientes = pacienteService.obtenerPacientesPorMedicoAutenticado(pageable);
        return ApiResponseDTO.success("Los pacientes han sido creados con exito",HttpStatus.OK.value(),pacientes,HttpStatus.OK);
    }

    /**
     * Endpoint que sobre un idPaciente realiza una busqueda comprobando:
     * 1- El Medico que realiza la consulta sea el medico asociado con el paciente
     * 2- El paciente sobre el que se realiza la consulta exista realmente.
     * @param idPaciente identificador del paciente sobre el que se quiere realizar la búsqueda
     * @return APIResponse con la información del paciente sobre el que se ha realizado la consulta.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<PacienteResponseDTO>> obtenerPacienteDeMedicoAutenticado(@PathVariable("id") Long idPaciente){
        //Comprueba datos del usuario authenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long idMedico = obtenerMedicoService.obtenerIdMedicoPorMail(auth.getName());
        //Comprueba que el paciente está asociado con el medico authenticado
        Paciente paciente = pacienteService.findPacienteByIdAndMedicoId(idPaciente, idMedico);
        PacienteResponseDTO pacienteResponseDTO = pacienteMapper.toPacienteResponseDTO(paciente);

        return ApiResponseDTO.success("Paciente encontrado con éxito", HttpStatus.OK.value(), pacienteResponseDTO, HttpStatus.OK);
    }

}
