package com.serdacar.infollution.model;

import java.io.Serializable;

public class Estacion implements Serializable {

    private int codigoCorto;
    private String nombre;
    private String direccion;
    private double latitud;
    private double longitud;

    public Estacion(int codigoCorto, String nombre, String direccion, double latitud, double longitud) {
        this.codigoCorto = codigoCorto;
        this.nombre = nombre;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public void setCodigoCorto(int codigoCorto) {
        this.codigoCorto = codigoCorto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public int getCodigoCorto() {
        return codigoCorto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }
}
