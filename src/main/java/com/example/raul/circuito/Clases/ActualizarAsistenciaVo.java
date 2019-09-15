package com.example.raul.circuito.Clases;

public class ActualizarAsistenciaVo {
    private String ID_Mes;
    private String Mes;
    private String ReuEntreSemana;
    private String ReuFinSemana;
    private String Nombre;

    public ActualizarAsistenciaVo(){

    }

    public ActualizarAsistenciaVo(String id_mes, String mes, String reuEntreSemana, String reuFinSemana, String nombre) {
        ID_Mes = id_mes;
        Mes = mes;
        ReuEntreSemana = reuEntreSemana;
        ReuFinSemana = reuFinSemana;
        Nombre = nombre;
    }

    public String getID_Mes() {
        return ID_Mes;
    }

    public void setID_Mes(String ID_Mes) {
        this.ID_Mes = ID_Mes;
    }

    public String getMes() {
        return Mes;
    }

    public void setMes(String mes) {
        Mes = mes;
    }

    public String getReuEntreSemana() {
        return ReuEntreSemana;
    }

    public void setReuEntreSemana(String reuEntreSemana) {
        ReuEntreSemana = reuEntreSemana;
    }

    public String getReuFinSemana() {
        return ReuFinSemana;
    }

    public void setReuFinSemana(String reuFinSemana) {
        ReuFinSemana = reuFinSemana;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
