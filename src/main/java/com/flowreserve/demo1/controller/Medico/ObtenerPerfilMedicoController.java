package com.flowreserve.demo1.controller.Medico;

import com.flowreserve.demo1.dto.Medico.MedicoDTO;
import com.flowreserve.demo1.dto.Medico.MedicoProfileDTO;
import com.flowreserve.demo1.dto.global.ApiResponseDTO;
import com.flowreserve.demo1.mapper.MedicoMapper;
import com.flowreserve.demo1.model.Medico.Medico;
import com.flowreserve.demo1.service.Medico.MedicoService;
import com.flowreserve.demo1.service.Medico.ObtenerMedicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ObtenerPerfilMedicoController {

    private final ObtenerMedicoService obtenerMedicoService;
    private final MedicoMapper medicoMapper;

    /**
     * Enpoint que recibe el ID de un médico y devuelve el perfil público de ese médico.
     * @param id identificador del médico del que se quiere obtener la información
     * @return ResponseEntity con la información proporcionada de ese médico.
     */
    @GetMapping("/medico/{id}")
    public ResponseEntity<ApiResponseDTO<MedicoProfileDTO>> obtenerMedicoById(@PathVariable Long id){
        Medico medico = obtenerMedicoService.obtenerMedicoPorId(id);
        MedicoProfileDTO medicoProfileDTO = medicoMapper.toMedicoProfileDTO(medico);
        return ApiResponseDTO.success("Médico encontrado con éxito", medicoProfileDTO, HttpStatus.OK);
    }
}
