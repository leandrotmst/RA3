package com.example.ra3.domains;

public class Setor {
    private String nome;
    private String descricao;
    private String responsavel;

    public Setor(String nome, String descricao, String responsavel) {
        this.nome = nome;
        this.descricao = descricao;
        this.responsavel = responsavel;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }
}
