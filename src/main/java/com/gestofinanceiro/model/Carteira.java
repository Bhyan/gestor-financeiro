package com.gestofinanceiro.model;

import br.ufrn.imd.stonks.framework.framework.model.DespesaFramework;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Carteira extends DespesaFramework {

    public Carteira(@NotNull(message = "Usuário é obrigatorio.") Usuario usuario) {
        super(usuario);
    }

    public Carteira(DespesaFramework despesa) {
        super(despesa.getUsuario());
    }

    public Carteira() {
    }
}