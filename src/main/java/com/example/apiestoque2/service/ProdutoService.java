package com.example.apiestoque2.service;

import com.example.apiestoque2.exception.InsufficientStockException;
import com.example.apiestoque2.model.ProdutoModel;
import com.example.apiestoque2.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<ProdutoModel> listarProdutos() {
        return produtoRepository.findAll();
    }

    public ProdutoModel getById(Integer id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
    }

    @Transactional
    public ProdutoModel salvarProduto(ProdutoModel produto) {
        return produtoRepository.save(produto);
    }

    @Transactional
    public void excluirProduto(Integer id) {
        ProdutoModel produtoModel = this.getById(id);
        produtoRepository.delete(produtoModel);
    }

    @Transactional
    public void atualizarProduto(Integer id, ProdutoModel produto) {
        ProdutoModel p = this.getById(id);
        BeanUtils.copyProperties(produto, p);

        produtoRepository.save(p);
    }

    @Transactional
    public void alterarProduto(Integer id, ProdutoModel produtoModel) {
        ProdutoModel p = this.getById(id);
//
//        if (produto.isEmpty()) {
//            throw new IllegalArgumentException("Nenhum campo foi informado na atualização");
//        }
//
//        produtoValidation.validate(produto);
//
//        produto.forEach((chave, valor) -> {
//            try {
//                Field field = ProdutoModel.class.getDeclaredField(chave);
//                field.setAccessible(true);
//                field.set(p, valor);
//            } catch (NoSuchFieldException | IllegalAccessException e) {
//                throw new RuntimeException(e);
//            }
//        });
//
//        produtoRepository.save(p);
        if (produtoModel.getNome() != null) {
            p.setNome(produtoModel.getNome());
        }
        if (produtoModel.getPreco() != null) {
            p.setPreco(produtoModel.getPreco());
        }
        if (produtoModel.getQuantidadeEstoque() != null) {
            p.setQuantidadeEstoque(produtoModel.getQuantidadeEstoque());
        }
        if (produtoModel.getDescricao() != null) {
            p.setDescricao(produtoModel.getDescricao());
        }
//        BeanUtils.copyProperties(produtoModel, p);
        produtoRepository.save(p);
    }

    @Transactional
    public Integer lancarBaixaEstoque(Integer id, Integer quantidade) {
        ProdutoModel p = this.getById(id);
        if (p.getQuantidadeEstoque() < quantidade) {
            throw new InsufficientStockException("Estoque é insuficiente para a baixa");
        }

        Integer novoEstoque = p.getQuantidadeEstoque() - quantidade;

        p.setQuantidadeEstoque(novoEstoque);
        produtoRepository.save(p);

        return novoEstoque;
    }
}
