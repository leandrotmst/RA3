package com.example.ra3;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Contato {
    private final StringProperty nome;
    private final StringProperty email;

    public Contato(String nome, String email) {
        this.nome = new SimpleStringProperty(nome);
        this.email = new SimpleStringProperty(email);
    }

    // Getters para as propriedades (essencial para o TableView)
    public StringProperty nomeProperty() { return nome; }
    public StringProperty emailProperty() { return email; }

    // Getters e Setters convencionais
    public String getNome() { return nome.get(); }
    public void setNome(String nome) { this.nome.set(nome); }

    public String getEmail() { return email.get(); }
    public void setEmail(String email) { this.email.set(email); }
}