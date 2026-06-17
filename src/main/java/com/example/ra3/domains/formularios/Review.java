package com.example.ra3.domains.formularios;

import java.time.LocalDate;

public class Review {
    String solucao;
    String resultado;
    LocalDate dataRegistro;

    public Review(String solucao, String resultado, LocalDate dataRegistro) {
        this.solucao = solucao;
        this.resultado = resultado;
        this.dataRegistro = dataRegistro;

    }

    public String getSolucao() { return solucao; }
    public void setSolucao(String solucao) { this.solucao = solucao; }

    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }

    public LocalDate getDataRegistro() { return dataRegistro; }
    public void setDataRegistro(LocalDate dataRegistro) { this.dataRegistro = dataRegistro; }
}
