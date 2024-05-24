/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.POJO;

/**
 *
 * @author froyl
 */
public class Mantenimiento {
    private int idMantenimiento;
    private String tipo; 

    public Mantenimiento() {
    }

    public int getIdMantenimiento() {
        return idMantenimiento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setIdMantenimiento(int idMantenimiento) {
        this.idMantenimiento = idMantenimiento;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    @Override
    public String toString() {
        return this.tipo;
    }
}
