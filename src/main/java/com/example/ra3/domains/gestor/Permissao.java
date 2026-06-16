package com.example.ra3.domains.gestor;

import java.io.Serializable;

public class Permissao implements Serializable {
    private String nome;
    private String nivel;
    private String modulo;
    private String gestorEmail;

    public Permissao(String nome, String nivel, String modulo, String gestorEmail) {
        this.nome = nome;
        this.nivel = nivel;
        this.modulo = modulo;
        this.gestorEmail = gestorEmail;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    public String getModulo() { return modulo; }
    public void setModulo(String modulo) { this.modulo = modulo; }

    public String getGestorEmail() { return gestorEmail; }
    public void setGestorEmail(String gestorEmail) { this.gestorEmail = gestorEmail; }
}