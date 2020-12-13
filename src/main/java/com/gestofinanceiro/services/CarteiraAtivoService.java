package com.gestofinanceiro.services;

import br.ufrn.imd.stonks.framework.framework.model.DespesaAtivoFramework;
import br.ufrn.imd.stonks.framework.framework.model.DespesaAtivoValorFramework;
import br.ufrn.imd.stonks.framework.framework.service.DespesaAtivoServiceAbstract;
import com.gestofinanceiro.model.Ativo;
import com.gestofinanceiro.model.Carteira;
import com.gestofinanceiro.model.CarteiraAtivo;
import com.gestofinanceiro.model.CarteiraAtivoValor;
import com.gestofinanceiro.repository.CarteiraAtivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarteiraAtivoService extends DespesaAtivoServiceAbstract {

    @Autowired
    CarteiraAtivoRepository carteiraAtivoRepository;

    public List<CarteiraAtivo> findByAtivosCarteiraCompra(int id) {
        return carteiraAtivoRepository.findByAtivosCarteiraCompra(id);
    }

    public Ativo[] listarAtivos(Carteira carteira) {
        CarteiraAtivo[] carteiraAtivo = carteiraAtivoRepository.findAllByDespesa(carteira);

        Ativo[] ativos = new Ativo[carteiraAtivo.length];
        for (int i = 0; i < carteiraAtivo.length; i ++) {
            ativos[i] = (Ativo) carteiraAtivo[i].getAtivo();
        }
        return ativos;
    }

    public Optional<CarteiraAtivo> findById(int id) {
        return carteiraAtivoRepository.findById(id);
    }

    public Double totalCarteira(Integer idCarteira){
        return carteiraAtivoRepository.totalCarteira(idCarteira);
    }

    //Todo
    @Override
    public List<DespesaAtivoValorFramework> gerarDadosRelatorio(List<DespesaAtivoFramework> ativos) {
        List<DespesaAtivoValorFramework> carteiraAtivoValorList = new ArrayList<>();

        for (DespesaAtivoFramework ativo : ativos) {
            CarteiraAtivoValor carteiraAtivoValor = new CarteiraAtivoValor();
            carteiraAtivoValor.setDespesaAtivo(ativo);

            try {
                carteiraAtivoValor.setValor((float) ativo.getValor());
                carteiraAtivoValor.setLucro((float) ativo.getValor());
            } catch (Exception e) {
                e.printStackTrace();
            }

            carteiraAtivoValorList.add(carteiraAtivoValor);
        }

        return carteiraAtivoValorList;
    }
}