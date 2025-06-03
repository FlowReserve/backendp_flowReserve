package com.flowreserve.demo1.dto.global;

import lombok.Builder;
import lombok.Getter;
import java.time.Instant;

@Getter
@Builder
public class ApiResponseDTO<T> {

    private boolean status;
    private String message;
    private Integer serverCode;
    private T responseObject;

    @Builder.Default
    private Instant timeStamp = Instant.now();

}