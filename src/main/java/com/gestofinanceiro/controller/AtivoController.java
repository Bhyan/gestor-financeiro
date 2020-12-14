package com.gestofinanceiro.controller;

import com.gestofinanceiro.model.*;
import com.gestofinanceiro.services.AtivoService;
import com.gestofinanceiro.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

@Controller
public class AtivoController {

    @Autowired
    private AtivoService ativoService;

    @Autowired
    private UsuarioService usuarioService;

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
        Ativo ativo = new Ativo();
        ativo.setUsuario(usuarioLogado);
        modelAndView.addObject("ativo", ativo);
        modelAndView.addObject("usuario", usuarioLogado);
        modelAndView.setViewName("/ativo/create");

        return modelAndView;
    }

    @RequestMapping(value = "/ativo/cadastrar", method = RequestMethod.POST)
    public String create(@Valid Ativo ativo) {

        ativoService.save(ativo);

        return "redirect:/ativo/index";
    }

    @RequestMapping(value = "/ativo/escolherTipoAtivo", method = RequestMethod.GET)
    public ModelAndView escolherTipoAtivo() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/ativo/cadastrar");
        Usuario usuarioLogado = usuarioService.usuarioLogado();

        modelAndView.addObject("usuario", usuarioLogado);
        modelAndView.addObject("selecaoAtivo", new SelecaoAtivo());
        modelAndView.addObject("operacoes", null);

        return modelAndView;
    }

    @RequestMapping(value = "/ativo/escolherTipoAtivo", method = RequestMethod.POST)
    public ModelAndView cadastrar(@Valid TipoAtivo tipoAtivo) {
        ModelAndView modelAndView = new ModelAndView();
        Usuario usuarioLogado = usuarioService.usuarioLogado();

        modelAndView.addObject("usuario", usuarioLogado);

        switch (tipoAtivo) {
            case AGUA:
                modelAndView.setViewName("/ativo/cadastroAgua");
                Agua agua = new Agua();
                agua.setUsuario(usuarioLogado);
                modelAndView.addObject("agua", agua);
                break;
            case SUPERMERCADO:
                modelAndView.setViewName("/ativo/cadastroSupermercado");
                modelAndView.addObject("supermercado", new Supermercado());
                break;
            case SAUDE:
                modelAndView.setViewName("/ativo/cadastroSaude");
                modelAndView.addObject("saude", new Saude());
                break;
            case ENERGIA:
                modelAndView.setViewName("/ativo/cadastroEnergia");
                modelAndView.addObject("energia", new Energia());
                break;
            case OUTROS:
                modelAndView.setViewName("/ativo/cadastroOutros");
                modelAndView.addObject("outros", new Outros());
                break;
        }
        return modelAndView;
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

    @RequestMapping(value = "/ativo/cadastrarAgua", method = RequestMethod.POST)
    public ModelAndView cadastroAgua(
            HttpServletRequest request,
            @Valid Agua agua,
            BindingResult bindingResult,
            ModelMap modelMap){
        ModelAndView modelAndView = new ModelAndView();
        Usuario usuarioLogado = usuarioService.usuarioLogado();
        modelAndView.addObject("usuario", usuarioLogado);

        agua.setUsuario(usuarioLogado);
        agua.setCodigo(agua.getConta().toString());
        ativoService.save(agua);

        modelAndView.setViewName("redirect:/ativo/index");

        return modelAndView;
    }

    @RequestMapping(value = "/ativo/cadastrarSupermercado", method = RequestMethod.POST)
    public ModelAndView cadastroSupermercado(
            HttpServletRequest request,
            @Valid Supermercado supermercado,
            BindingResult bindingResult,
            ModelMap modelMap){
        ModelAndView modelAndView = new ModelAndView();
        Usuario usuarioLogado = usuarioService.usuarioLogado();
        modelAndView.addObject("usuario", usuarioLogado);

        supermercado.setUsuario(usuarioLogado);
        supermercado.setCodigo(supermercado.getLocalizacao());
        ativoService.save(supermercado);

        modelAndView.setViewName("redirect:/ativo/index");

        return modelAndView;
    }

    @RequestMapping(value = "/ativo/cadastrarEnergia", method = RequestMethod.POST)
    public ModelAndView cadastrarEnergia(
            HttpServletRequest request,
            @Valid Energia energia,
            BindingResult bindingResult,
            ModelMap modelMap){
        ModelAndView modelAndView = new ModelAndView();
        Usuario usuarioLogado = usuarioService.usuarioLogado();
        modelAndView.addObject("usuario", usuarioLogado);

        energia.setUsuario(usuarioLogado);
        energia.setCodigo(energia.getContrato().toString());
        ativoService.save(energia);

        modelAndView.setViewName("redirect:/ativo/index");

        return modelAndView;
    }

    @RequestMapping(value = "/ativo/cadastrarSaude", method = RequestMethod.POST)
    public ModelAndView cadastroSaude(
            HttpServletRequest request,
            @Valid Saude saude,
            BindingResult bindingResult,
            ModelMap modelMap) {

        ModelAndView modelAndView = new ModelAndView();
        Usuario usuarioLogado = usuarioService.usuarioLogado();
        modelAndView.addObject("usuario", usuarioLogado);

        saude.setUsuario(usuarioLogado);
        saude.setCodigo(saude.getNumeroConvenio().toString());
        ativoService.save(saude);

        modelAndView.setViewName("redirect:/ativo/index");

        return modelAndView;
    }

    @RequestMapping(value = "/ativo/cadastrarOutros", method = RequestMethod.POST)
    public ModelAndView cadastroOutros(
            HttpServletRequest request,
            @Valid Outros outros,
            BindingResult bindingResult,
            ModelMap modelMap) {

        ModelAndView modelAndView = new ModelAndView();
        Usuario usuarioLogado = usuarioService.usuarioLogado();
        modelAndView.addObject("usuario", usuarioLogado);

        outros.setUsuario(usuarioLogado);
        outros.setCodigo(outros.getDescricao());
        ativoService.save(outros);

        modelAndView.setViewName("redirect:/ativo/index");

        return modelAndView;
    }

    @RequestMapping(value = "/ativo/remove/{id}")
    public String remove(@PathVariable("id") int id) {
        ativoService.delete(ativoService.getById(id).get());

        return "redirect:/ativo/index";
    }
}
