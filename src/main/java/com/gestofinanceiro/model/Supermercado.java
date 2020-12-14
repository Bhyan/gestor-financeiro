package com.gestofinanceiro.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Supermercado extends Ativo{
    @Column(nullable = true)
    private String localizacao;

    public Supermercado() {
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
}
