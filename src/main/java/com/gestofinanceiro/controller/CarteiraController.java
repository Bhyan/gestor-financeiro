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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;

@Controller
public class CarteiraController {

    @Autowired
    private CarteiraService carteiraService;

    @Autowired
    private CarteiraAtivoService carteiraAtivoService;

    @Autowired
    private AtivoService ativoService;

    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping(value = "/carteira/index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        Usuario usuarioLogado = usuarioService.usuarioLogado();
        Carteira carteira = carteiraService.carteiraByUsuario(usuarioLogado);

        modelAndView.setViewName("/carteira/index");
        if (carteira != null) {
            modelAndView.addObject("ativosCarteira", carteiraAtivoService.findByAtivosDespesa(carteira.getId(), null));
        } else {
            modelAndView.addObject("ativosCarteira",null);
        }
        modelAndView.addObject("usuario", usuarioLogado);

        return modelAndView;
    }

    @RequestMapping(value = "/carteira/cadastrar", method = RequestMethod.POST)
    public ModelAndView create(@Valid CarteiraAtivo carteiraAtivo,
                               BindingResult bindingResult,
                               @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataTransacao,
                               @RequestParam Ativo ativo,
                               ModelMap modelMap) {

        ModelAndView modelAndView = new ModelAndView();

        carteiraAtivo.setDataTransacao(dataTransacao);
        carteiraAtivo.setAtivo(ativo);
        Usuario usuarioLogado = usuarioService.usuarioLogado();

        try {
            carteiraService.adicionar(carteiraAtivo, usuarioLogado);
        } catch (Exception e) {
            modelAndView.addObject("errorFlash", e.getMessage());
        }

        Carteira carteira = (Carteira) carteiraService.despesaByUsuario();

        modelAndView.addObject("ativosCarteira",
                carteiraAtivoService.findByAtivosDespesa(carteira.getId(), null));
        modelAndView.addObject("usuario", usuarioLogado);

        modelAndView.setViewName("/carteira/index");

        return modelAndView;
    }

    @RequestMapping(value = "/carteira/editar/{id}")
    public ModelAndView edit(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        Usuario usuarioLogado = usuarioService.usuarioLogado();

        modelAndView.addObject("ativos", ativoService.findAll());
        modelAndView.addObject("carteiraAtivo", carteiraAtivoService.findById(id).get());
        modelAndView.addObject("usuario", usuarioLogado);

        modelAndView.setViewName("/carteira/edit");

        return modelAndView;
    }

    @RequestMapping(value = "/carteira/cadastrar", method = RequestMethod.GET)
    public ModelAndView cadastrarCarteira() {
        Usuario usuarioLogado = usuarioService.usuarioLogado();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("ativos", ativoService.findAll());
        modelAndView.addObject("carteiraAtivo", new CarteiraAtivo());
        modelAndView.addObject("usuario", usuarioLogado);
        modelAndView.setViewName("/carteira/create");

        return modelAndView;
    }

    @RequestMapping(value = "/carteira/editar/{id}", method = RequestMethod.POST)
    public ModelAndView update(@PathVariable("id") int id, HttpServletRequest request,
                               @Valid CarteiraAtivo carteiraAtivo,
                               BindingResult bindingResult,
                               @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataTransacao,
                               @RequestParam Ativo ativo,
                               ModelMap modelMap) {
        Usuario usuarioLogado = usuarioService.usuarioLogado();
        Carteira carteira = carteiraService.carteiraByUsuario(usuarioLogado);

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("ativosCarteira", carteiraAtivoService.findByAtivosDespesa(carteira.getId(), null));
        modelAndView.addObject("usuario", usuarioLogado);
        modelAndView.setViewName("/carteira/index");

        Optional<CarteiraAtivo> carteiraAtivoInstance = carteiraAtivoService.findById(id);

        if (!carteiraAtivoInstance.isPresent()) {
            modelAndView.addObject("errorFlash", "Ativo não encontrado");
            return modelAndView;
        }
        carteiraAtivo.setDespesa(carteiraAtivoInstance.get().getDespesa());
        carteiraAtivo.setAtivo(ativo);
        carteiraAtivoService.salvar(carteiraAtivo);

        modelAndView.addObject("successFlash", "Ativo Atualizado");
        modelAndView.setViewName("/carteira/index");

        return modelAndView;
    }

    @RequestMapping(value = "/carteira/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") int id) {
        carteiraService.deleteById(id);

        return "redirect:/dashboard/home";
    }
}