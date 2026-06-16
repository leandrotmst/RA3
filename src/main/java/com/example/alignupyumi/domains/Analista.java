package com.example.alignupyumi.domains;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//Classe de domínio que representa um Analista - implementa Serializable para permitir persistência em arquivo (Exemplo 10 - pasta JavaFX - estudos)

public class Analista implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String email;
    private String cpf;
    private LocalDate dataNascimento;
    private String senha;

    // Construtor vazio necessário para serialização.
    public Analista() {}

    public Analista(String nome, String email, String cpf, LocalDate dataNascimento, String senha) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.senha = senha;
    }

    // Getters e Setters
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

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    //Método para formatar a data no padrão brasileiro (dd/MM/aaaa)

    public String getDataNascimentoFormatada() {
        if (dataNascimento == null) return "";
        return dataNascimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    // toString para facilitar impressão (debug)
    @Override
    public String toString() {
        return "Analista{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", cpf='" + cpf + '\'' +
                ", dataNascimento=" + dataNascimento +
                '}';
    }
}
