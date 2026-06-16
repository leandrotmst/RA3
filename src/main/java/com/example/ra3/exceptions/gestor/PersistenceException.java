package com.example.ra3.exceptions.gestor;

public class PersistenceException extends Exception {

    public PersistenceException(String mensagem) {
        super(mensagem);
    }

    public PersistenceException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}