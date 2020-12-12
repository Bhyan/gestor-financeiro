package com.gestofinanceiro.model;

import br.ufrn.imd.stonks.framework.framework.model.UsuarioFramework;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="usuario")
public class Usuario extends UsuarioFramework {

    @NotNull(message = "CPF é obrigatorio.")
    private String cpf;

    @Column( name = "data_nascimento")
    private Date dataNascimento;

    @ManyToMany( cascade = CascadeType.ALL)
    @JoinTable(name = "usuario_role", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public Usuario(@NotNull(message = "Nome é obrigatorio.") String nome,
                   @NotNull(message = "Email é obrigatorio.") @Email(message = "Email invalido") String email,
                   @NotNull(message = "Senha é obrigatorio.") String password,
                   @NotNull(message = "CPF é obrigatorio.") String cpf,
                   Boolean status, Date dataNascimento) {

        super(nome, email, password, status);
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    public Usuario() { }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
