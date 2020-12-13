package com.gestofinanceiro.services;


import br.ufrn.imd.stonks.framework.framework.model.DespesaAtivoFramework;
import br.ufrn.imd.stonks.framework.framework.model.DespesaFramework;
import br.ufrn.imd.stonks.framework.framework.service.DespesaServiceAbstract;
import com.gestofinanceiro.model.Carteira;
import com.gestofinanceiro.model.Usuario;
import com.gestofinanceiro.repository.CarteiraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarteiraService extends DespesaServiceAbstract {

    @Autowired
    CarteiraRepository carteiraRepository;

    @Autowired
    UsuarioService usuarioService;

    public void salvarCarteira(Carteira carteira) {
        carteiraRepository.save(carteira);
    }

    public boolean isAlreadyPresent(Carteira carteira) {
        return carteiraRepository.findById(carteira.getId()).isPresent();
    }

    public Optional<Carteira> findById(int id){
        return carteiraRepository.findById(id);
    }

    public Carteira carteiraByUsuario(Usuario usuario) {
        return carteiraRepository.findByUsuario(usuario);
    }

    public void deleteById(int id) {
        carteiraRepository.deleteById(id);
    }

    @Override
    public DespesaAtivoFramework adicionarAtivo(DespesaFramework despesa, DespesaAtivoFramework despesaAtivo) {
        despesaAtivo.setDespesa(despesa);
        return despesaAtivo;
    }

    @Override
    public boolean removerAtivo(DespesaAtivoFramework despesaAtivo) {
        return false;
    }

    @Override
    public DespesaFramework despesaByUsuario() {
        Usuario usuarioLogado = usuarioService.usuarioLogado();
        return carteiraByUsuario(usuarioLogado);
    }

    @Override
    public void salvarDespesa(DespesaFramework despesa) {
        Carteira carteira = new Carteira(despesa);
        carteiraRepository.save(carteira);
    }
}