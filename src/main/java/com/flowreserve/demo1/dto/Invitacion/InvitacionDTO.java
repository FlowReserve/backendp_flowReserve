package com.flowreserve.demo1.dto.Invitacion;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
@JsonIgnoreProperties(value = {"codigo", "usada"}, allowGetters = true)

public class InvitacionDTO {

    @NotNull(message = "El id es obligatorio")

    private Long hospitalId;




    private String codigo;

    private boolean usada;

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public boolean getUsada() {
        return usada;
    }

    public void setUsada(boolean usada) {
        this.usada = usada;
    }
}