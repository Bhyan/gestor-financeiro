package com.gestofinanceiro.controller;

import br.ufrn.imd.stonks.framework.framework.model.DespesaAtivoFramework;
import com.gestofinanceiro.model.Carteira;
import com.gestofinanceiro.model.Usuario;
import com.gestofinanceiro.services.CarteiraAtivoService;
import com.gestofinanceiro.services.CarteiraService;
import com.gestofinanceiro.services.EmailService;
import com.gestofinanceiro.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping
public class CarteiraAtivoController {

    @Autowired
    private CarteiraAtivoService carteiraAtivoService;

    @Autowired
    private CarteiraService carteiraService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmailService emailService;

    @GetMapping(value = "/carteiraativo/relatorio")
    public String imprimirRelatorio(Model model) {

        Usuario usuario = usuarioService.usuarioLogado();
        model.addAttribute("usuario", usuario);

        Carteira carteira = carteiraService.carteiraByUsuario(usuario);

        List<DespesaAtivoFramework> ativos = carteiraAtivoService.findByAtivosDespesa(carteira.getId(), null);

        model.addAttribute("ativosCarteira", carteiraAtivoService.gerarDadosRelatorio(ativos));

        return "dashboard/imprimirRelatorio";
    }

    @GetMapping(value = "/carteiraativo/relatorio/enviar")
    public String enviarRelatorio(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("dashboard/imprimirRelatorio");

        Usuario usuario = usuarioService.usuarioLogado();
        Carteira carteira = carteiraService.carteiraByUsuario(usuario);

        List<DespesaAtivoFramework> ativos = carteiraAtivoService.findByAtivosDespesa(carteira.getId(), null);

        String mensagemEmail = emailService.montarCorpoEmail(ativos);

        model.addAttribute("ativosCarteira", ativos);
        model.addAttribute("usuario", usuario);

        if (emailService.enviarEmail(mensagemEmail, usuario)) {
            modelAndView.addObject("successMessage", "Relatório enviado por email com sucesso.");
        } else {
            modelAndView.addObject("failMessage", "Relatório não pode ser enviado.");
        }

        return "redirect:/carteiraativo/relatorio";
    }
}
