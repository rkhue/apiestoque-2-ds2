package com.example.apiestoque2.controller;

import com.example.apiestoque2.model.ProdutoModel;
import com.example.apiestoque2.service.ProdutoService;
import com.example.apiestoque2.validation.OnCreate;
import com.example.apiestoque2.validation.OnPatch;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin("*")
public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoRepository) {
        this.produtoService = produtoRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoModel> getById(@PathVariable Integer id) {
        ProdutoModel produtoModel = produtoService.getById(id);
        return ResponseEntity.ok(produtoModel);
    }


    @GetMapping("/selecionar")
    public ResponseEntity<List<ProdutoModel>> listarProdutos() {
        return ResponseEntity.ok(produtoService.listarProdutos());
    }


    @PostMapping("/inserir")
    public ResponseEntity<Object> inserirProduto(@RequestBody @Validated({OnCreate.class, Default.class}) ProdutoModel produto) {
        // include validations manually via ifs by now (at least)
        if (produto.getPreco() <= 0) {
            return ResponseEntity.badRequest().body("O preço do produto deve ser maior que zero");
        } else if (produto.getQuantidadeEstoque() < 0) {
            return ResponseEntity.badRequest().body("A quantidade de estoque deve ser maior ou igual a zero");
        }
        ProdutoModel p = produtoService.salvarProduto(produto);

        return ResponseEntity.ok(p);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> excluirProduto(@PathVariable Integer id) {
        produtoService.excluirProduto(id);
        return ResponseEntity.ok("Produto excluído com sucesso!");
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarProduto(@PathVariable Integer id,
                                                   @Validated({OnPatch.class, Default.class}) @RequestBody ProdutoModel produto) {
        produtoService.atualizarProduto(id, produto);
        return ResponseEntity.ok("Produto atualizado com sucesso!");

    }

    @PatchMapping("/alterar/{id}")
    public ResponseEntity<String> alterarProduto(@PathVariable Integer id,
                                                 @Validated({OnPatch.class, Default.class}) ProdutoModel produto) {
        produtoService.alterarProduto(id, produto);
        return ResponseEntity.ok("Produto parcialmente atualizado com sucesso!");
    }

    @PutMapping("/baixar_estoque/{id}")
    public ResponseEntity<Integer> baixarEstoque(@PathVariable Integer id, @RequestBody Integer quantidade) {
        Integer estoque = produtoService.lancarBaixaEstoque(id, quantidade);
        return ResponseEntity.ok(estoque);
    }
}
