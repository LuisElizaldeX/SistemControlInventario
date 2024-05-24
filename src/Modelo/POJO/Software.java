/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.POJO;

/**
 *
 * @author froyl
 */
public class Software {
    private int idSoftware;
    private String nombre;
    private String peso;
    private int arquitectura;

    public Software() {
    }

    public int getIdSoftware() {
        return idSoftware;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPeso() {
        return peso;
    }

    public int getArquitectura() {
        return arquitectura;
    }

    public void setIdSoftware(int idSoftware) {
        this.idSoftware = idSoftware;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public void setArquitectura(int arquitectura) {
        this.arquitectura = arquitectura;
    }
}
