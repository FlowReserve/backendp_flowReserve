package com.flowreserve.demo1.dto.Request;

public class RequestDTO {
    private String pressureA;

    private String pressureB;

    private String comentarios;

    private Long idPaciente;

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }
    public String getPressureA() {
        return pressureA;
    }

    public void setPressureA(String pressureA) {
        this.pressureA = pressureA;
    }




    public String getPressureB() {
        return pressureB;
    }

    public void setPressureB(String pressureB) {
        this.pressureB = pressureB;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}


