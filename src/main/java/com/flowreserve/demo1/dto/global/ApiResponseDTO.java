package com.flowreserve.demo1.dto.global;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

@Getter
public class ApiResponseDTO<T> {

    private final boolean status;
    private final String message;
    private final Integer serverCode;
    private final T responseObject;
    private final Instant timeStamp;

    @Builder
    protected ApiResponseDTO(boolean status, String message, Integer serverCode, T responseObject) {
        this.status = status;
        this.message = message;
        this.serverCode = serverCode;
        this.responseObject = responseObject;
        this.timeStamp = Instant.now();
    }

    // Método principal para construir una ResponseEntity
    public static <T> ResponseEntity<ApiResponseDTO<T>> build(
            boolean status,
            String message,
            Integer serverCode,
            T responseObject,
            HttpStatus httpStatus
    ) {
        ApiResponseDTO<T> response = ApiResponseDTO.<T>builder()
                .status(status)
                .message(message)
                .serverCode(serverCode)
                .responseObject(responseObject)
                .build();

        return new ResponseEntity<>(response, httpStatus);
    }

    // Método para respuestas de éxito
    public static <T> ResponseEntity<ApiResponseDTO<T>> success(
            String message,
            Integer serverCode,
            T responseObject,
            HttpStatus httpStatus
    ) {
        return build(true, message, serverCode, responseObject, httpStatus);
    }

    // Método para respuestas de error
    public static <T> ResponseEntity<ApiResponseDTO<T>> error(
            String message,
            Integer serverCode,
            HttpStatus httpStatus
    ) {
        return build(false, message, serverCode, null, httpStatus);
    }
}
