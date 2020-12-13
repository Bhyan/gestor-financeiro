package com.gestofinanceiro.model;

import br.ufrn.imd.stonks.framework.framework.model.AtivoFramework;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ativo")
public class Ativo extends AtivoFramework {

    public Ativo() { }
}
