package com.example.ra3.domains;

public class Cargo {
    private String titulo;
    private String tipo;
    private String descricao;

    public Cargo(String titulo, String tipo, String descricao) {
        this.titulo = titulo;
        this.tipo = tipo;
        this.descricao = descricao;
    }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}