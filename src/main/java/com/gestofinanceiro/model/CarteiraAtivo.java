package com.gestofinanceiro.model;

import br.ufrn.imd.stonks.framework.framework.model.DespesaAtivoFramework;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class CarteiraAtivo extends DespesaAtivoFramework {

    public CarteiraAtivo(
            @NotNull(message = "Carteira é obrigatoria.") Carteira carteira,
            @NotNull(message = "Ativo é obrigatorio.") Ativo ativo,
            @NotNull(message = "Valor é obrigatorio.") double valor,
            @NotNull(message = "Quantidade é obrigatoria.") int quantidade,
            @NotNull(message = "Data da compra é obrigatória.") Date data_compra) {

        super(carteira, ativo, valor, quantidade, data_compra);
    }

    public CarteiraAtivo() { }
}