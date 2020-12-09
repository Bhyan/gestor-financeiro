package com.gestofinanceiro.model;

import br.ufrn.imd.stonks.framework.framework.model.Despesa;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Carteira extends Despesa {

    public Carteira(@NotNull(message = "Usuário é obrigatorio.") Usuario usuario) {
        super(usuario);
    }

    public Carteira() {
    }
}