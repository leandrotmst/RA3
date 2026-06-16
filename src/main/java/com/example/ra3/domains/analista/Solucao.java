package com.example.ra3.domains.analista;

import java.io.Serializable;

//Classe de domínio para as soluções propostas pelo analista - implementa Serializable para persistência
//Cada solução pertence a um analista (emailAnalista)

public class Solucao implements Serializable {

    private static final long serialVersionUID = 1L;

    private String titulo;
    private String descricao;
    private String equipe;
    private String emailAnalista; // para saber qual analista criou a solução

    public Solucao() {}

    public Solucao(String titulo, String descricao, String equipe, String emailAnalista) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.equipe = equipe;
        this.emailAnalista = emailAnalista;
    }

    // Getters e Setters
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEquipe() {
        return equipe;
    }
    public void setEquipe(String equipe) {
        this.equipe = equipe;
    }

    public String getEmailAnalista() {
        return emailAnalista;
    }
    public void setEmailAnalista(String emailAnalista) {
        this.emailAnalista = emailAnalista;
    }

    @Override
    public String toString() {
        return "Solucao{" +
                "titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", equipe='" + equipe + '\'' +
                '}';
    }
}