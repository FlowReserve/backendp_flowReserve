package com.flowreserve.demo1.dto.Hospital;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class HospitalDTO {

    public String getNombreHospital() {
        return nombreHospital;
    }

    public void setNombreHospital(String nombreHospital) {
        this.nombreHospital = nombreHospital;
    }


    @NotBlank(message = "El nombre es obligatorio")

    private String nombreHospital;

    @NotNull(message = "El código es obligatorio")
    private Long codigoHospital;


    public Long getCodigoHospital() {
        return codigoHospital;
    }

    public void setCodigoHospital(Long codigoHospital) {
        this.codigoHospital = codigoHospital;
    }
}
