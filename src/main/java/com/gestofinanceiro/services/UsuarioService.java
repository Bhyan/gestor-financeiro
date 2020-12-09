package com.gestofinanceiro.services;

import com.gestofinanceiro.exception.CpfInvalidoException;
import com.gestofinanceiro.exception.UsuarioExistenteException;
import com.gestofinanceiro.model.Usuario;

import java.util.Optional;

public interface UsuarioService {

    public void salvarUsuario(Usuario usuario) throws UsuarioExistenteException, CpfInvalidoException;

    public Boolean isUserAlreadyPresent(Usuario usuario);

    public Usuario usuarioPorEmail(String email);

    public Optional<Usuario> findById(int id);

    public void deleteById(int id);

    public Usuario usuarioLogado();
}