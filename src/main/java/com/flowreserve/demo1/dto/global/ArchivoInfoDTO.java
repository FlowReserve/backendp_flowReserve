package com.flowreserve.demo1.dto.global;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Builder
@Getter
@Setter
public class ArchivoInfoDTO {
    private String filename;
    private String contentType;
    private Long size;
}
