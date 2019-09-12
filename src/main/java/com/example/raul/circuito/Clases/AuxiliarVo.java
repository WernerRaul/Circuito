package com.example.raul.circuito.Clases;

public class AuxiliarVo {
    private String nombre;
    private String añomes;
    private String horas;
    private String revisitas;
    private String estudios;

    public AuxiliarVo(){

    }

    public AuxiliarVo(String nombre, String añomes, String horas, String revisitas, String estudios) {
        this.nombre = nombre;
        this.añomes = añomes;
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

    public String getAñomes() {
        return añomes;
    }

    public void setAñomes(String añomes) {
        this.añomes = añomes;
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
