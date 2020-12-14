package com.gestofinanceiro.model;

public enum TipoAtivo {
    SUPERMERCADO{
        @Override
        public String getDemonacao() {
            return "Supermercado";
        }
    },
    SAUDE{
        @Override
        public String getDemonacao() {
            return "Saúde";
        }
    },
    AGUA{
        @Override
        public String getDemonacao() {
            return "Agua";
        }
    },
    ENERGIA{
        @Override
        public String getDemonacao() {
            return "Energia";
        }
    },
    OUTROS{
        @Override
        public String getDemonacao() {
            return "Outros";
        }
    };

    public abstract String getDemonacao();
}
