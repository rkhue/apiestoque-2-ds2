package com.example.apiestoque2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "produto")
@Entity
@Validated
public class ProdutoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O nome do produto deve ser informado e não nulo")
    @NotNull(message = "O nome do produto deve ser informado e não nulo")
    @Size(max=100, message = "O nome do produto não pode ter mais de 100 caracteres")
    private String nome;

    @NotBlank
    private String descricao;

    @NotNull(message = "O preço do produto deve ser informado e não nulo")
    @DecimalMin(value = "0.01", message = "O preço do produto deve ser maior que zero")
    private Double preco;

    @NotNull(message = "O nome do produto deve ser informado e não nulo")
    @PositiveOrZero(message = "A quantidade de estoque deve ser maior ou igual a zero")
    @Column(name = "quantidadeestoque")
    private Integer quantidadeEstoque;


    @Override
    public String toString() {
        return "ProdutoModel{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", preco=" + preco +
                ", quantidadeEstoque=" + quantidadeEstoque +
                '}';
    }
}
