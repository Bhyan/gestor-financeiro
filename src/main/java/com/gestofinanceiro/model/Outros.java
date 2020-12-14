package com.gestofinanceiro.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Outros extends Ativo {

    @Column(nullable = true)
    private String descricao;

    public Outros() {
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
