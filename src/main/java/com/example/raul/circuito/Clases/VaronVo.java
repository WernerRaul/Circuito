package com.example.raul.circuito.Clases;

public class VaronVo {
    private String nombre;
    private String edad;
    private String bautizado;
    private String anciano;
    private String sm;
    private String precregular;
    private String horas;
    private String revisitas;
    private String estudios;

    public VaronVo(){

    }

    public VaronVo(String nombre, String edad, String bautizado, String anciano,
                   String sm, String precregular, String horas, String revisitas, String estudios) {
        this.nombre = nombre;
        this.edad = edad;
        this.bautizado = bautizado;
        this.anciano = anciano;
        this.sm = sm;
        this.precregular = precregular;
        this.horas = horas;
        this.revisitas = revisitas;
        this.estudios = estudios;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getBautizado() {
        return bautizado;
    }

    public void setBautizado(String bautizado) {
        this.bautizado = bautizado;
    }

    public String getAnciano() {
        return anciano;
    }

    public void setAnciano(String anciano) {
        this.anciano = anciano;
    }

    public String getSm() {
        return sm;
    }

    public void setSm(String sm) {
        this.sm = sm;
    }

    public String getPrecregular() {
        return precregular;
    }

    public void setPrecregular(String precregular) {
        this.precregular = precregular;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public String getRevisitas() {
        return revisitas;
    }

    public void setRevisitas(String revisitas) {
        this.revisitas = revisitas;
    }

    public String getEstudios() {
        return estudios;
    }

    public void setEstudios(String estudios) {
        this.estudios = estudios;
    }
}
