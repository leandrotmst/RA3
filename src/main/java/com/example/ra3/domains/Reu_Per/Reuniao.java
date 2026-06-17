package com.example.ra3.domains.Reu_Per;

import java.io.Serializable;

public class Reuniao implements Serializable {
    private String titulo;
    private String data;
    private String horario;

    public Reuniao(String titulo, String data, String horario) {
        this.titulo = titulo;
        this.data = data;
        this.horario = horario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
}