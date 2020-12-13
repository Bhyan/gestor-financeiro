package com.gestofinanceiro.controller;

import com.gestofinanceiro.model.Usuario;
import com.gestofinanceiro.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    @Autowired
    private UsuarioService usuarioService;

    @Override
    public String getErrorPath() {
        return null;
    }

    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        ModelAndView modelAndView = new ModelAndView();
        Usuario usuarioLogado = usuarioService.usuarioLogado();

        if (usuarioLogado != null) {
            modelAndView.addObject("usuario", usuarioLogado);
        }

        if(status != null){
            Integer statusCode = Integer.valueOf(status.toString());
            if(statusCode == HttpStatus.NOT_FOUND.value()){
                modelAndView.setViewName("404");
                return modelAndView;
            }else if ( statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()){
                modelAndView.setViewName("500");
                return modelAndView;
            }
        }

        modelAndView.setViewName("error");
        return modelAndView;
    }
}
