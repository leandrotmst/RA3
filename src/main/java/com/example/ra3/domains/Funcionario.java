package com.example.ra3.domains;

import java.io.Serializable;

public class Funcionario implements Serializable {
    String nome;
    String email;
    String equipe;

    public Funcionario(String nome, String email, String equipe) {
        this.nome = nome;
        this.email = email;
        this.equipe = equipe;
    }

    // Getters e Setters convencionais
    public String getNome() { return nome; }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEquipe() { return equipe; }
    public void setEquipe(String equipe) {
        this.equipe = equipe;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        this.email = email;
    }
}
