package com.gestofinanceiro.controller;

import com.gestofinanceiro.model.Carteira;
import com.gestofinanceiro.model.Usuario;
import com.gestofinanceiro.services.CarteiraAtivoService;
import com.gestofinanceiro.services.CarteiraService;
import com.gestofinanceiro.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthenticationController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CarteiraService carteiraService;

    @Autowired
    private CarteiraAtivoService carteiraAtivoService;

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView();
        Usuario usuario = new Usuario();
        modelAndView.addObject("user", usuario);
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("dashboard/home");

        Usuario usuarioLogado = usuarioService.usuarioLogado();
        Carteira carteira = carteiraService.carteiraByUsuario(usuarioLogado);

        modelAndView.addObject("usuario", usuarioLogado);

        double sum = 0;

        if (carteira != null) {
            Double total = carteiraAtivoService.totalCarteira(carteira.getId());
            sum = total != null ? total : 0;
        }

        modelAndView.addObject("carteiraAtivos", carteira != null ?
                carteiraAtivoService.findByAtivosCarteiraCompra(carteira.getId()) : null);
        modelAndView.addObject("total", sum);

        return modelAndView;
    }
}
