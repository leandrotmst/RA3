package com.example.ra3.domains;

import java.io.Serializable;

public class Funcionario implements Serializable {
    private String nome;
    private String email;
    private String equipe;
    private String gestorEmail;

    public Funcionario(String nome, String email, String equipe, String gestorEmail) {
        this.nome = nome;
        this.email = email;
        this.equipe = equipe;
        this.gestorEmail = gestorEmail;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEquipe() { return equipe; }
    public void setEquipe(String equipe) { this.equipe = equipe; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getGestorEmail() { return gestorEmail; }
    public void setGestorEmail(String gestorEmail) { this.gestorEmail = gestorEmail; }
}
