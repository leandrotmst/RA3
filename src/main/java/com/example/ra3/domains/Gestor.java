package com.example.ra3.domains;

public class Gestor {
    String nome;
    String email;
    String telefone;

    public Gestor(String nome, String email, String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    // Getters e Setters convencionais
    public String getNome() { return nome; }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        this.email = email;
    }
}