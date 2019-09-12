package com.example.raul.circuito.Clases;

import android.app.Application;

public class GlobalClass   extends Application {

    private Integer idCongregacion;
    private String nombrePublicador;
    private  String fechaA;
    private String fechaB;

    public String getFechaA() {
        return fechaA;
    }

    public void setFechaA(String fechaA) {
        this.fechaA = fechaA;
    }

    public String getFechaB() {
        return fechaB;
    }

    public void setFechaB(String fechaB) {
        this.fechaB = fechaB;
    }

    public String getNombrePublicador() {
        return nombrePublicador;
    }

    public void setNombrePublicador(String nombrePublicador) {
        this.nombrePublicador = nombrePublicador;
    }

    public Integer getIdCongregacion() {
        return idCongregacion;
    }

    public void setIdCongregacion(Integer idCongregacion) {
        this.idCongregacion = idCongregacion;
    }
}
