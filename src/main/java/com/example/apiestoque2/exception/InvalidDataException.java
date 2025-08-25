package com.example.apiestoque2.exception;

import lombok.Getter;

import java.util.Map;

public class InvalidDataException extends RuntimeException {
    @Getter
    private final Map<String, String> errors;

    public InvalidDataException(Map<String, String> errors) {
        super("Dados inválidos");
        this.errors = errors;
    }
}
