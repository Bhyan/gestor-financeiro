package com.gestofinanceiro.model;

import br.ufrn.imd.stonks.framework.framework.model.AtivoFramework;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="ativo")
public class Ativo extends AtivoFramework {

    private Operacao operacao;

    @NotNull
    @ManyToOne
    @JoinColumn(
            name = "usuario_id"
    )
    private Usuario usuario;

    public Ativo() { }

    public Operacao getOperacao() {
        return operacao;
    }

    public void setOperacao(Operacao operacao) {
        this.operacao = operacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
