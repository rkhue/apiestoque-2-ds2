package com.example.apiestoque2.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String message) {
        super(message);
    }

    public InsufficientStockException() {
        super("Quantidade de estoque insuficiente");
    }
}
