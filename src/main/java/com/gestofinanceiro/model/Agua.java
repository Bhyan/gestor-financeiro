package com.gestofinanceiro.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Agua extends Ativo {

    @Column(nullable = true)
    private Integer conta;

    public Agua() {
    }

    public Integer getConta() {
        return conta;
    }

    public void setConta(Integer conta) {
        this.conta = conta;
    }
}
