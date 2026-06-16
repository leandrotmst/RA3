package com.example.ra3.domains;

public class MembroSistema {
    private String nome;
    private String email;
    private String cargo;

    public MembroSistema(String nome, String email, String cargo) {
        this.nome = nome;
        this.email = email;
        this.cargo = cargo;
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getCargo() { return cargo; }
}