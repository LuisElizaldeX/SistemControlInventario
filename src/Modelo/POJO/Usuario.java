/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.POJO;

/**
 *
 * @author froyl
 */
public class Usuario {
    private int idUsuario;
    private String cargo;
    private String nombreCompleto;
    private String correoInstitucional;
    private String contrasenia;
    private byte[] foto;
    private int idCentroComputo;

    public Usuario() {
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getCargo() {
        return cargo;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public int getIdCentroComputo() {
        return idCentroComputo;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public void setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public void setIdCentroComputo(int idCentroComputo) {
        this.idCentroComputo = idCentroComputo;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return this.nombreCompleto;
    }
}
