package com.example.raul.circuito.Clases;

public class RegularVo {
    private String nombre;
    private String horas;
    private String revisitas;
    private String estudios;

    public RegularVo(){

    }


    public RegularVo(String nombre, String horas, String revisitas, String estudios) {
        this.nombre = nombre;
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
