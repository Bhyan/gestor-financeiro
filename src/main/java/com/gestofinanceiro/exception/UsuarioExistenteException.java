package com.gestofinanceiro.exception;

public class UsuarioExistenteException extends Exception {
    public UsuarioExistenteException(String mensage) {
        super(mensage);
    }
}
