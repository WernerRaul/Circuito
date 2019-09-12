package com.example.raul.circuito.Clases;

public class ActividadVo {
    private String ID;
    private String AnoMes;
    private String Horas;
    private String Revisitas;
    private String Estudios;
    private String Auxiliar;

    public ActividadVo() {

    }

    public ActividadVo(String ID, String anoMes, String horas, String revisitas, String estudios, String auxiliar) {
        this.ID = ID;
        AnoMes = anoMes;
        Horas = horas;
        Revisitas = revisitas;
        Estudios = estudios;
        Auxiliar = auxiliar;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAnoMes() {
        return AnoMes;
    }

    public void setAnoMes(String anoMes) {
        AnoMes = anoMes;
    }

    public String getHoras() {
        return Horas;
    }

    public void setHoras(String horas) {
        Horas = horas;
    }

    public String getRevisitas() {
        return Revisitas;
    }

    public void setRevisitas(String revisitas) {
        Revisitas = revisitas;
    }

    public String getEstudios() {
        return Estudios;
    }

    public void setEstudios(String estudios) {
        Estudios = estudios;
    }

    public String getAuxiliar() {
        return Auxiliar;
    }

    public void setAuxiliar(String auxiliar) {
        Auxiliar = auxiliar;
    }
}