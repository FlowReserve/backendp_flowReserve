package com.flowreserve.demo1.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PacienteDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombrePaciente;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellidoPaciente;

    @NotNull(message = "El código CNHC es obligatorio")
    private String codigoCNHC;


    private Long codigoAsociadoAcnhc;

    // Getters y setters

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getApellidoPaciente() {
        return apellidoPaciente;
    }

    public void setApellidoPaciente(String apellidoPaciente) {
        this.apellidoPaciente = apellidoPaciente;
    }




    public String getCodigoCNHC() {
        return codigoCNHC;
    }

    public void setCodigoCNHC(String codigoCNHC) {
        this.codigoCNHC = codigoCNHC;
    }
}
