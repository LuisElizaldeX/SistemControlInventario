/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.POJO;

/**
 *
 * @author froyl
 */
public class CentroComputo {
    private int idCentroComputo;
    private String facultad;
    private String direccion;
    private String aula;
    private String edificio;
    private String piso;

    public CentroComputo() {
    }

    public int getIdCentroComputo() {
        return idCentroComputo;
    }

    public String getFacultad() {
        return facultad;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getAula() {
        return aula;
    }

    public String getEdificio() {
        return edificio;
    }

    public String getPiso() {
        return piso;
    }

    public void setIdCentroComputo(int idCentroComputo) {
        this.idCentroComputo = idCentroComputo;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    @Override
    public String toString() {
        return "Facultad: " + this.facultad + " - Edificio: " + this.edificio + " - Aula: " + this.aula;
    }
}
