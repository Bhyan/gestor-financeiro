package com.gestofinanceiro.exception;

public class CpfInvalidoException extends Exception {
    public CpfInvalidoException(String cpf_inválido) {
        super(cpf_inválido);
    }
}
