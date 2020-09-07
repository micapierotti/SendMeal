package com.tripleM.sendmeal_lab01.model;

public class CuentaBancaria {
    private String cbu;
    private String alias;

    public CuentaBancaria(String cbu, String alias) {
        this.cbu = cbu;
        this.alias = alias;
    }

    public String getCbu() {
        return cbu;
    }

    public String getAlias() {
        return alias;
    }

    @Override
    public String toString() {
        return "CuentaBancaria{" +
                "cbu='" + cbu + '\'' +
                ", alias='" + alias + '\'' +
                '}';
    }
}
