package com.gestofinanceiro.model;

public enum Operacao {
    RENDA {
        @Override
        public String getDenominacao() {
            return "Renda";
        }
    },
    GASTO {
        @Override
        public String getDenominacao() {
            return "Gasto";
        }

    };

    public abstract String getDenominacao();
}
