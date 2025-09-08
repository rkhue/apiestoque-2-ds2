package com.example.apiestoque2.controller;

import com.example.apiestoque2.dto.produto.ProdutoRequestDTO;
import com.example.apiestoque2.dto.produto.ProdutoResponseDTO;
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
    public ResponseEntity<ProdutoResponseDTO> getById(@PathVariable Integer id) {
        ProdutoResponseDTO produtoModel = produtoService.getByIdResponse(id);
        return ResponseEntity.ok(produtoModel);
    }


    @GetMapping("/selecionar")
    public ResponseEntity<List<ProdutoResponseDTO>> listarProdutos() {
        return ResponseEntity.ok(produtoService.listarProdutosResponse());
    }


    @PostMapping("/inserir")
    public ResponseEntity<ProdutoResponseDTO> inserirProduto(@RequestBody @Validated({OnCreate.class, Default.class}) ProdutoRequestDTO produto) {
        // include validations manually via ifs by now (at least)
        ProdutoResponseDTO p = produtoService.salvarProduto(produto);

        return ResponseEntity.ok(p);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> excluirProduto(@PathVariable Integer id) {
        produtoService.excluirProduto(id);
        return ResponseEntity.ok("Produto excluído com sucesso!");
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarProduto(@PathVariable Integer id,
                                                   @Validated({OnCreate.class, Default.class}) @RequestBody ProdutoRequestDTO produto) {
        produtoService.atualizarProduto(id, produto);
        return ResponseEntity.ok("Produto atualizado com sucesso!");

    }

    @PatchMapping("/alterar/{id}")
    public ResponseEntity<String> alterarProduto(@PathVariable Integer id,
                                                 @Validated({OnPatch.class, Default.class}) @RequestBody ProdutoRequestDTO produto) {
        produtoService.alterarProduto(id, produto);
        return ResponseEntity.ok("Produto parcialmente atualizado com sucesso!");
    }

    @PutMapping("/baixar_estoque/{id}")
    public ResponseEntity<Integer> baixarEstoque(@PathVariable Integer id, @RequestBody Integer quantidade) {
        Integer estoque = produtoService.lancarBaixaEstoque(id, quantidade);
        return ResponseEntity.ok(estoque);
    }
}
