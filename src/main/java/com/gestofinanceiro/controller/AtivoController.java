package com.gestofinanceiro.controller;

import com.gestofinanceiro.model.Ativo;
import com.gestofinanceiro.model.Carteira;
import com.gestofinanceiro.model.CarteiraAtivo;
import com.gestofinanceiro.model.Usuario;
import com.gestofinanceiro.services.AtivoService;
import com.gestofinanceiro.services.CarteiraAtivoService;
import com.gestofinanceiro.services.CarteiraService;
import com.gestofinanceiro.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;

@Controller
public class AtivoController {

    @Autowired
    private AtivoService ativoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CarteiraService carteiraService;

    @Autowired
    private CarteiraAtivoService carteiraAtivoService;

    @GetMapping("/ativo/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();

        Usuario usuarioLogado = usuarioService.usuarioLogado();

        modelAndView.addObject("ativosCarteira", ativoService.findByUsuario(usuarioLogado));
        modelAndView.addObject("usuario", usuarioLogado);
        modelAndView.setViewName("ativo/index");

        return modelAndView;
    }

    @RequestMapping(value = "/ativo/cadastrar", method = RequestMethod.GET)
    public ModelAndView cadastrarCarteira() {
        Usuario usuarioLogado = usuarioService.usuarioLogado();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("ativo", new Ativo());
        modelAndView.addObject("usuario", usuarioLogado);
        modelAndView.setViewName("/ativo/create");

        return modelAndView;
    }

    @RequestMapping(value = "/ativo/cadastrar", method = RequestMethod.POST)
    public String create(@Valid Ativo ativo) {

        Usuario usuarioLogado = usuarioService.usuarioLogado();
        ativo.setUsuario(usuarioLogado);
        ativoService.save(ativo);

        return "redirect:/ativo/index";
    }

    @RequestMapping(value = "/ativo/editar/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        Usuario usuarioLogado = usuarioService.usuarioLogado();

        modelAndView.addObject("ativo", ativoService.getById(id).get());
        modelAndView.addObject("usuario", usuarioLogado);

        modelAndView.setViewName("/ativo/edit");

        return modelAndView;
    }

    @RequestMapping(value = "/ativo/editar/{id}", method = RequestMethod.POST)
    public String update(@Valid Ativo ativo) {
        ativoService.save(ativo);

        return "redirect:/ativo/index";
    }

    @RequestMapping(value = "/ativo/remove/{id}")
    public String remove(@PathVariable("id") int id) {

        ativoService.delete(ativoService.getById(id).get());

        return "redirect:/ativo/index";
    }
}
