package com.flowreserve.demo1.dto.global;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

@Getter
public class ApiResponseDTO<T> {

    private boolean status;
    private String message;
    private Integer serverCode;
    private T responseObject;
    private Instant timeStamp;

    @Builder
    protected ApiResponseDTO(boolean status, String message, int serverCode, T responseObject) {
        this.status = status;
        this.message = message;
        this.serverCode = serverCode;
        this.responseObject = responseObject;
        this.timeStamp = Instant.now();
    }

    /**
     * Constructor para generar una salida de API key exitosa
     * @param message mensaje de salida del API
     * @param code código de salida del servidor
     * @param responseObject Objeto que se envia al front-end
     * @param httpStatus Código HTTP propio de la consulta
     * @return ApiResponseDTO que contiene un objeto <T> con los datos de la solicitud
     * @param <T> Objeto ResponseEntity que contiene la salida personalizada + codigo HTTP Status
     */
    public static <T> ResponseEntity<ApiResponseDTO<T>> success(
            String message,
            Integer code,
            T responseObject,
            HttpStatus httpStatus
    ) {
        ApiResponseDTO<T> response = ApiResponseDTO.<T>builder()
                .status(true)
                .message(message)
                .serverCode(code)
                .responseObject(responseObject)
                .build();

        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * Constructor para generar una salida de API key con error
     * @param message mensaje de salida del API
     * @param code código de salida del servidor
     * @param httpStatus Código HTTP propio de la consulta
     * @return ApiResponseDTO que contiene un objeto <T> con los datos de la solicitud
     * @param <T> Objeto ResponseEntity que contiene la salida personalizada + codigo HTTP Status
     */

    public static <T> ResponseEntity<ApiResponseDTO<T>> error(
            String message,
            Integer code,
            HttpStatus httpStatus
    ) {
        ApiResponseDTO<T> response = ApiResponseDTO.<T>builder()
                .status(false)
                .message(message)
                .serverCode(code)
                .responseObject(null)
                .build();

        return new ResponseEntity<>(response, httpStatus);
    }

}