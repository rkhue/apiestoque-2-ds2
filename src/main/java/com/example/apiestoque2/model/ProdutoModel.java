package com.example.apiestoque2.model;

import com.example.apiestoque2.validation.OnCreate;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "produto")
@Entity
public class ProdutoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //    @NotBlank(message = "O nome do produto deve ser informado e não nulo", groups = OnCreate.class)
    @NotNull(message = "O nome do produto deve ser informado e não nulo", groups = OnCreate.class)
    @Pattern(regexp = ".*\\S.*\\S*.*", message = "O nome do produto deve ser de 2 à 50 caracteres alfanuméricos latinos.")
    @Size(max=100, message = "O nome do produto não pode ter mais de 100 caracteres")
    private String nome;

    private String descricao;

    @NotNull(message = "O preço do produto deve ser informado e não nulo", groups = OnCreate.class)
    @DecimalMin(value = "0.01", message = "O preço do produto deve ser maior que zero")
    private Double preco;

    @NotNull(message = "O nome do produto deve ser informado e não nulo", groups = OnCreate.class)
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
