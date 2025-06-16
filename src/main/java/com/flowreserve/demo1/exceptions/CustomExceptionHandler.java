package com.flowreserve.demo1.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.flowreserve.demo1.dto.global.ApiResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class CustomExceptionHandler {

    //Captura las excepciones y genera un objeto ApiResponseDTO personalizado de error con la estructura de salida.

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleEntityNotfound(EntityNotFoundException e){
        return ApiResponseDTO.error(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleDuplicateKey(DuplicateKeyException e){
        return ApiResponseDTO.error(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CustomExceptions.UnmodifiableRequestException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleIlegalStateException(CustomExceptions.UnmodifiableRequestException e){
        return ApiResponseDTO.error(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleEnumParseException(HttpMessageNotReadableException ex) {
        if (ex.getCause() instanceof InvalidFormatException invalidFormat) {
            if (invalidFormat.getTargetType().isEnum()) {
                return ApiResponseDTO.error("Estado proporcionado inválido. Valores permitidos: PENDIENTE, EN_PROCESO, COMPLETADA, CANCELADA.", HttpStatus.BAD_REQUEST);
            }
        }

        return ApiResponseDTO.error("Error en el formato de la solicitud.", HttpStatus.BAD_REQUEST);
    }

}
