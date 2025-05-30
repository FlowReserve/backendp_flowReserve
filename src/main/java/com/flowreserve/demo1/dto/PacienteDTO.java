package com.flowreserve.demo1.dto;

public class PacienteDTO {

    private String nombrePaciente;

    private String apellidoPaciente;

    public String getApellidoPaciente() {
        return apellidoPaciente;
    }

    public void setApellidoPaciente(String apellidoPaciente) {
        this.apellidoPaciente = apellidoPaciente;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public Long getCodigoCNHC() {
        return CodigoCNHC;
    }

    public void setCodigoCNHC(Long codigoCNHC) {
        CodigoCNHC = codigoCNHC;
    }

    public Long getCodigoAsociadoAcnhc() {
        return CodigoAsociadoAcnhc;
    }

    public void setCodigoAsociadoAcnhc(Long codigoAsociadoAcnhc) {
        CodigoAsociadoAcnhc = codigoAsociadoAcnhc;
    }

    private Long CodigoCNHC;

     private Long CodigoAsociadoAcnhc;
}
