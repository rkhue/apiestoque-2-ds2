package com.example.apiestoque2.dto.produto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProdutoResponseDTO {
    private Integer id;
    private String nome;
    private Double preco;
    private String descricao;
}
