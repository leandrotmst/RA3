package com.example.ra3.domains.administrador;

import java.io.Serializable;

public class Gestor implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;
    private String email;
    private String senha;
    private boolean verificado;

    public Gestor() {
    }

    public Gestor(String nome, String email, String senha, boolean verificado) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.verificado = verificado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }

    public String getStatusVerificacao() {
        return verificado ? "Verificado" : "Pendente";
    }

    @Override
    public String toString() {
        return "Gestor{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", verificado=" + verificado +
                '}';
    }
}
