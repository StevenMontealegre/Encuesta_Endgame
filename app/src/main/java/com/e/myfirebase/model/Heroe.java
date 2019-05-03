package com.e.myfirebase.model;

public class Heroe {
    private String nombre, gusto, cambio, aparicion, uid;

    public Heroe() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getGusto() {
        return gusto;
    }

    public void setGusto(String gusto) {
        this.gusto = gusto;
    }

    public String getCambio() {
        return cambio;
    }

    public void setCambio(String cambio) {
        this.cambio = cambio;
    }

    public String getAparicion() {
        return aparicion;
    }

    public void setAparicion(String aparicion) {
        this.aparicion = aparicion;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
