package com.flowreserve.demo1.controller.Medico;

import com.flowreserve.demo1.dto.Medico.MedicoDTO;
import com.flowreserve.demo1.dto.Medico.MedicoProfileDTO;
import com.flowreserve.demo1.dto.Medico.MedicoResponseDTO;
import com.flowreserve.demo1.dto.global.ApiResponseDTO;
import com.flowreserve.demo1.mapper.MedicoMapper;
import com.flowreserve.demo1.mapper.PacienteMapper;
import com.flowreserve.demo1.model.Medico.Medico;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import com.flowreserve.demo1.service.Medico.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/medicos")
public class MedicoController {
    @Autowired
    private  MedicoMapper medicoMapper;


    private final MedicoService medicoService;

    @Autowired
    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }


    @PostMapping
    public ResponseEntity<ApiResponseDTO<MedicoProfileDTO>> crearMedico(@Valid @RequestBody MedicoDTO medicoDTO) {


        Medico medicoCreado = medicoService.crearMedicoDesdeInvitacion(medicoDTO);
        MedicoProfileDTO medicoProfileDTO= medicoMapper.toMedicoProfileDTO(medicoCreado);


        return ApiResponseDTO.success("Medico creado  con éxito", medicoProfileDTO, HttpStatus.CREATED);


    }
}













