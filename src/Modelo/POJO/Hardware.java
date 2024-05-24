/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.POJO;

import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author froylan
 */
public class Hardware {
    private int idHardware;
    private String modelo;
    private String numeroSerie;
    private String estado;
    private String procesador;
    private String posicion;
    private float almacenamiento;
    private float ram;
    private String direccionMac;
    private String direccionIp;
    private String grafica;
    private Date fechaIngreso;
    private String sistemaOperativo;
    private int arquitectura;
    private String marca;
    private String tarjetaMadre;
    private int idCentroComputo;
    private String aula;

    public Hardware() {
    }

    public int getIdHardware() {
        return idHardware;
    }

    public String getModelo() {
        return modelo;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public String getEstado() {
        return estado;
    }

    public String getProcesador() {
        return procesador;
    }

    public String getPosicion() {
        return posicion;
    }

    public float getAlmacenamiento() {
        return almacenamiento;
    }

    public float getRam() {
        return ram;
    }

    public String getDireccionMac() {
        return direccionMac;
    }

    public String getDireccionIp() {
        return direccionIp;
    }

    public String getGrafica() {
        return grafica;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public String getSistemaOperativo() {
        return sistemaOperativo;
    }

    public int getArquitectura() {
        return arquitectura;
    }

    public String getMarca() {
        return marca;
    }

    public String getTarjetaMadre() {
        return tarjetaMadre;
    }

    public int getIdCentroComputo() {
        return idCentroComputo;
    }

    public String getAula() {
        return aula;
    }

    public void setIdHardware(int idHardware) {
        this.idHardware = idHardware;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public void setAlmacenamiento(float almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    public void setRam(float ram) {
        this.ram = ram;
    }

    public void setDireccionMac(String direccionMac) {
        this.direccionMac = direccionMac;
    }

    public void setDireccionIp(String direccionIp) {
        this.direccionIp = direccionIp;
    }

    public void setGrafica(String grafica) {
        this.grafica = grafica;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public void setSistemaOperativo(String sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }

    public void setArquitectura(int arquitectura) {
        this.arquitectura = arquitectura;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setTarjetaMadre(String tarjetaMadre) {
        this.tarjetaMadre = tarjetaMadre;
    }

    public void setIdCentroComputo(int idCentroComputo) {
        this.idCentroComputo = idCentroComputo;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    @Override
    public String toString() {
        return this.numeroSerie;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.idHardware;
        hash = 59 * hash + Objects.hashCode(this.modelo);
        hash = 59 * hash + Objects.hashCode(this.numeroSerie);
        hash = 59 * hash + Objects.hashCode(this.estado);
        hash = 59 * hash + Objects.hashCode(this.procesador);
        hash = 59 * hash + Objects.hashCode(this.posicion);
        hash = 59 * hash + Float.floatToIntBits(this.almacenamiento);
        hash = 59 * hash + Float.floatToIntBits(this.ram);
        hash = 59 * hash + Objects.hashCode(this.direccionMac);
        hash = 59 * hash + Objects.hashCode(this.direccionIp);
        hash = 59 * hash + Objects.hashCode(this.grafica);
        hash = 59 * hash + Objects.hashCode(this.fechaIngreso);
        hash = 59 * hash + Objects.hashCode(this.sistemaOperativo);
        hash = 59 * hash + this.arquitectura;
        hash = 59 * hash + Objects.hashCode(this.marca);
        hash = 59 * hash + Objects.hashCode(this.tarjetaMadre);
        hash = 59 * hash + this.idCentroComputo;
        hash = 59 * hash + Objects.hashCode(this.aula);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Hardware other = (Hardware) obj;
        if (this.idHardware != other.idHardware) {
            return false;
        }
        if (Float.floatToIntBits(this.almacenamiento) != Float.floatToIntBits(other.almacenamiento)) {
            return false;
        }
        if (Float.floatToIntBits(this.ram) != Float.floatToIntBits(other.ram)) {
            return false;
        }
        if (this.arquitectura != other.arquitectura) {
            return false;
        }
        if (this.idCentroComputo != other.idCentroComputo) {
            return false;
        }
        if (!Objects.equals(this.modelo, other.modelo)) {
            return false;
        }
        if (!Objects.equals(this.numeroSerie, other.numeroSerie)) {
            return false;
        }
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        if (!Objects.equals(this.procesador, other.procesador)) {
            return false;
        }
        if (!Objects.equals(this.posicion, other.posicion)) {
            return false;
        }
        if (!Objects.equals(this.direccionMac, other.direccionMac)) {
            return false;
        }
        if (!Objects.equals(this.direccionIp, other.direccionIp)) {
            return false;
        }
        if (!Objects.equals(this.grafica, other.grafica)) {
            return false;
        }
        if (!Objects.equals(this.sistemaOperativo, other.sistemaOperativo)) {
            return false;
        }
        if (!Objects.equals(this.marca, other.marca)) {
            return false;
        }
        if (!Objects.equals(this.tarjetaMadre, other.tarjetaMadre)) {
            return false;
        }
        if (!Objects.equals(this.aula, other.aula)) {
            return false;
        }
        return Objects.equals(this.fechaIngreso, other.fechaIngreso);
    }
}
