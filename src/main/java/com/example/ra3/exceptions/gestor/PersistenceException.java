package com.example.ra3.exceptions.gestor;

public class PersistenceException extends Exception {
    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
    public PersistenceException(String message) {
        super(message);
    }
}
