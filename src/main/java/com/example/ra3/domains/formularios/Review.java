package com.example.ra3.domains.formularios;

public class Review {
    String solucao;
    String resultado;

    public Review(String solucao, String resultado) {
        this.solucao = solucao;
        this.resultado = resultado;
    }

    public String getSolucao() { return solucao; }
    public void setSolucao(String solucao) { this.solucao = solucao; }

    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }
}
