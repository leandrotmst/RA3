package com.example.ra3.domains.gestor;

import java.io.Serializable;

public class Reuniao implements Serializable {
    private String titulo;
    private String data;
    private String horario;
    private String gestorEmail;

    public Reuniao(String titulo, String data, String horario, String gestorEmail) {
        this.titulo = titulo;
        this.data = data;
        this.horario = horario;
        this.gestorEmail = gestorEmail;
    }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }

    public String getGestorEmail() { return gestorEmail; }
    public void setGestorEmail(String gestorEmail) { this.gestorEmail = gestorEmail; }
}