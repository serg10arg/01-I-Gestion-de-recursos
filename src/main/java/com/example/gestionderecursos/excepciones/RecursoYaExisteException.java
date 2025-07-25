package com.example.gestionderecursos.excepciones;

public class RecursoYaExisteException extends RuntimeException {
    public RecursoYaExisteException(String message) {
        super(message);
    }
}
