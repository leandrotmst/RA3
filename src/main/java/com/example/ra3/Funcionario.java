package com.example.ra3;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Funcionario {
    private final StringProperty nome;
    private final StringProperty email;
    private final StringProperty equipe;

    public Funcionario(String nome, String email, String equipe) {
        this.nome = new SimpleStringProperty(nome);
        this.email = new SimpleStringProperty(email);
        this.equipe = new SimpleStringProperty(equipe);
    }

    // Getters para as propriedades (essencial para o TableView)
    public StringProperty nomeProperty() { return nome; }
    public StringProperty emailProperty() { return email; }
    public StringProperty equipeProperty() { return equipe; }

    // Getters e Setters convencionais
    public String getNome() { return nome.get(); }
    public void setNome(String nome) { this.nome.set(nome); }

    public String getEquipe() { return equipe.get(); }
    public void setEquipe(String equipe) { this.equipe.set(equipe); }

    public String getEmail() { return email.get(); }
    public void setEmail(String email) { this.email.set(email); }
}