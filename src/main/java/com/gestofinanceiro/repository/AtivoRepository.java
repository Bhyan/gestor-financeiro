package com.gestofinanceiro.repository;

import com.gestofinanceiro.model.Ativo;
import com.gestofinanceiro.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtivoRepository extends JpaRepository<Ativo, Integer> {

    public List<Ativo> findAllByUsuario(Usuario usuario);
}
