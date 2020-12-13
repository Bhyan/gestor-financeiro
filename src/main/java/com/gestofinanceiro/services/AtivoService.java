package com.gestofinanceiro.services;

import com.gestofinanceiro.model.Ativo;
import com.gestofinanceiro.model.Usuario;
import com.gestofinanceiro.repository.AtivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AtivoService {

    @Autowired
    private AtivoRepository ativoRepository;

    public Ativo save(Ativo ativo) { return ativoRepository.save(ativo); }

    public List<Ativo> findByUsuario(Usuario usuario) {return ativoRepository.findAllByUsuario(usuario); }

    public Optional<Ativo> getById(int id) { return ativoRepository.findById(id);}

    public void delete(Ativo ativo) {
        ativoRepository.delete(ativo);
    }
}
