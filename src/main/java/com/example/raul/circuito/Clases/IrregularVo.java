package com.example.raul.circuito.Clases;

public class IrregularVo {
    private String nombre;
    private String anomes;

    public IrregularVo(){

    }

    public IrregularVo(String nombre, String anomes) {
        this.nombre = nombre;
        this.anomes = anomes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAnomes() {
        return anomes;
    }

    public void setAnomes(String anomes) {
        this.anomes = anomes;
    }
}

