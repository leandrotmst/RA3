package com.example.ra3.domains.formularios;

import java.time.LocalDate;

public class Resposta {
    String nivelEstresse;
    String resumo;
    LocalDate dataRegistro;

    public Resposta(String nivelEstresse, String resumo, LocalDate dataRegistro) {
        this.nivelEstresse = nivelEstresse;
        this.resumo = resumo;
        this.dataRegistro = dataRegistro;
    }

    public String getNivelEstresse() { return nivelEstresse; }
    public void setNivelEstresse(String nivelEstresse) {
        this.nivelEstresse = nivelEstresse;
    }

    public String getResumo() { return resumo; }
    public void setResumo(String resumo) { this.resumo = resumo; }

    public LocalDate getDataRegistro() { return dataRegistro; }
    public void setDataRegistro(LocalDate dataRegistro) { this.dataRegistro = dataRegistro; }
}
