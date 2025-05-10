package com.examenpoo;

import java.io.Serializable;

public class Mensaje implements Serializable {
    private static final long serialVersionUID = 1L;

    private String contenido;
    private String remitente;

    public Mensaje(String contenido, String remitente) {
        this.contenido = contenido;
        this.remitente = remitente;
    }
    public String getContenido() {
        return contenido;
    }

    public String getRemitente() {
        return remitente;
    }

    @Override
    public String toString() {
        return "Mensaje de " + remitente + ": " + contenido;
    }
}
