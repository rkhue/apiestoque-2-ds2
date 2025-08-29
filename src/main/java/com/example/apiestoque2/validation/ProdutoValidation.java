package com.example.apiestoque2.validation;

import com.example.apiestoque2.exception.InvalidDataException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProdutoValidation {


    @Transactional
    public void validate(Map<String, Object> produto) {

        Map<String, String> errors = new HashMap<>();

        if (produto.containsKey("nome")) {
            if (produto.get("nome") == null || produto.get("nome").toString().trim().isEmpty()) {
                errors.put("nome", "O nome do produto não pode ser vazio");
            }
        }

        // validar preço
        if (produto.containsKey("preco")) {
            Object precoObj = produto.get("preco");
            if (precoObj == null) {
                errors.put("preco", "O preço do produto não deve ser nulo");
            } else {
                String precoStr = precoObj.toString();

                if (precoStr.isEmpty()) {
                    errors.put("preco", "O preço do produto não deve ser vazio");
                }

                double preco = 0;
                try {
                    preco = Double.parseDouble(precoStr);
                } catch (NumberFormatException e) {
                    errors.put("preco", "O preço do produto deve ser um número decimal válido");
                }

                if (preco <= 0) {
                    errors.put("preco", "O preço do produto deve ser um número maior que zero");
                }
            }
        }

        // Validar estoque
        if (produto.containsKey("quantidadeEstoque")) {
            Object quantidadeObj = produto.get("quantidadeEstoque");
            if (quantidadeObj == null) {
                errors.put("quantidadeestoque", "A quantidade de estoque não pode ser nula");
            } else {
                String quantidadeStr = quantidadeObj.toString();

                if (quantidadeStr.isEmpty()) {
                    errors.put("quantidadeestoque", "A quantidade de estoque não pode ser vazia");
                }

                int quantidade = 0;
                try {
                    quantidade = Integer.parseInt(quantidadeStr);
                } catch (NumberFormatException e) {
                    errors.put("quantidadeestoque", "A quantidade de estoque deve ser um número inteiro");
                }

                if (quantidade < 0) {
                    errors.put("quantidadeestoque", "A quantidade de estoque deve ser maior ou igual a zero");
                }
            }

        }

        if (!errors.isEmpty()) {
            throw new InvalidDataException(errors);
        }
    }
}
