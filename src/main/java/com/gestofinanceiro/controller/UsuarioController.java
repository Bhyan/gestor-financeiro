package com.gestofinanceiro.controller;

import com.gestofinanceiro.exception.CpfInvalidoException;
import com.gestofinanceiro.exception.UsuarioExistenteException;
import com.gestofinanceiro.model.Usuario;
import com.gestofinanceiro.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @RequestMapping(value = "/usuario/register", method = RequestMethod.POST)
    public ModelAndView create(Usuario user, BindingResult bindingResult, ModelMap modelMap) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("failMessage", "Por favor corriga os erros.");
            modelMap.addAttribute("bindingResult", bindingResult);
        } else {
            try {
                usuarioService.salvarUsuario(user);
                modelAndView.addObject("successMessage", "Usuario registrado com sucesso.");
            } catch (UsuarioExistenteException | CpfInvalidoException e) {
                modelAndView.addObject("failMessage", e.getMessage());
                modelAndView.addObject("user", user);
                modelAndView.setViewName("register");
                return modelAndView;
            }
        }

        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/usuario/{id}", method = RequestMethod.POST)
    public String update(@PathVariable("id") int id, Usuario usuario) throws CpfInvalidoException, UsuarioExistenteException {
        Optional<Usuario> usuarioOptional = usuarioService.findById(id);

        if (usuarioOptional.isPresent()) {
            return "Usuário não encontrado.";
        }

        Usuario usuarioInstance = usuarioOptional.get();
        usuarioInstance.setNome(usuario.getNome());

        usuarioService.salvarUsuario(usuarioInstance);
        return "redirect:/register";
    }

    @RequestMapping(value = "/usuario/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") int id) {
        usuarioService.deleteById(id);

        return "redirect:/login";
    }
}