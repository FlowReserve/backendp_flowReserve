package com.flowreserve.demo1.dto.global;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ArchivoInfoDTO {
    private Long id;
    private String filename;
    private String contentType;
    private Long size;
    private String downloadUrl;
    private Instant fechaCreacion;
}
