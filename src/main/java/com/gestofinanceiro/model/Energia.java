package com.gestofinanceiro.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Energia extends Ativo {

    @Column(nullable = true)
    private Integer contrato;
    @Column(nullable = true)
    private String ligacao;

    public Energia() {
    }

    public Integer getContrato() {
        return contrato;
    }

    public void setContrato(Integer contrato) {
        this.contrato = contrato;
    }

    public String getLigacao() {
        return ligacao;
    }

    public void setLigacao(String ligacao) {
        this.ligacao = ligacao;
    }
}