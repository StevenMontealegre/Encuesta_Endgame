package com.e.myfirebase.model;

public class Persona {

    private String segmento;

    public Persona() {
    }

    public String getSegmento() {
        return segmento;
    }

    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }

    @Override
    public String toString() {
        return segmento;
    }
}
