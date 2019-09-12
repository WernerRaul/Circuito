package com.example.raul.circuito.Clases;

public class BajaactividadVo {
    private String nombre;
    private String horas;

    public BajaactividadVo(){

    }

    public BajaactividadVo(String nombre, String horas) {
        this.nombre = nombre;
        this.horas = horas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }
}

