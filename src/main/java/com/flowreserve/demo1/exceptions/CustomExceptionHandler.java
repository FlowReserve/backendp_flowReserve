package com.flowreserve.demo1.exceptions;

import com.flowreserve.demo1.dto.global.ApiResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return ApiResponseDTO.error(e.getMessage() , HttpStatus.CONFLICT);
    }

}
