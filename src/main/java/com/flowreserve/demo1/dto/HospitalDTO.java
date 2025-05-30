package com.flowreserve.demo1.dto;

public class HospitalDTO {

    public String getNombreHospital() {
        return nombreHospital;
    }

    public void setNombreHospital(String nombreHospital) {
        this.nombreHospital = nombreHospital;
    }



    private String nombreHospital;

    private Long codigoHospital;


    public Long getCodigoHospital() {
        return codigoHospital;
    }

    public void setCodigoHospital(Long codigoHospital) {
        this.codigoHospital = codigoHospital;
    }
}
