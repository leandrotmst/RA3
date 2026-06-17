package com.example.ra3.domains.formularios;

public class Resposta {
    String nivelEstresse;
    String resumo;

    public Resposta(String nivelEstresse, String resumo) {
        this.nivelEstresse = nivelEstresse;
        this.resumo = resumo;
    }

    public String getNivelEstresse() { return nivelEstresse; }
    public void setNivelEstresse(String nivelEstresse) {
        this.nivelEstresse = nivelEstresse;
    }

    public String getResumo() { return resumo; }
    public void setResumo(String resumo) { this.resumo = resumo; }
}
