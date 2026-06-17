package com.example.ra3.domains.Reu_Per;

import java.io.Serializable;

public class Permissao implements Serializable {
    private String nome;
    private String nivel;
    private String modulo;

    public Permissao(String nome, String nivel, String modulo) {
        this.nome = nome;
        this.nivel = nivel;
        this.modulo = modulo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }


    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }
}