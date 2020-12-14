package com.gestofinanceiro.model;

import br.ufrn.imd.stonks.framework.framework.model.AtivoFramework;

import javax.persistence.*;
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

    @Column(nullable = true)
    private String empresa;

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

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
}
