package com.gestofinanceiro.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Saude extends Ativo {

    @Column(nullable = true)
    private Integer numeroConvenio;

    public Saude() {
    }

    public Integer getNumeroConvenio() {
        return numeroConvenio;
    }

    public void setNumeroConvenio(Integer numeroConvenio) {
        this.numeroConvenio = numeroConvenio;
    }
}
