package com.gestofinanceiro.repository;

import com.gestofinanceiro.model.Carteira;
import com.gestofinanceiro.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteiraRepository extends JpaRepository<Carteira, Integer> {

    public Carteira findByid(int id);

    public Carteira findByUsuario(Usuario usuario);
}
