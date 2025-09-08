package com.example.apiestoque2.service;

import com.example.apiestoque2.dto.produto.ProdutoRequestDTO;
import com.example.apiestoque2.dto.produto.ProdutoResponseDTO;
import com.example.apiestoque2.exception.InsufficientStockException;
import com.example.apiestoque2.model.ProdutoModel;
import com.example.apiestoque2.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ObjectMapper objectMapper;

    public ProdutoService(ProdutoRepository produtoRepository, ObjectMapper objectMapper) {
        this.produtoRepository = produtoRepository;
        this.objectMapper = objectMapper;
    }


//    public ProdutoModel fromRequest(ProdutoRequestDTO produtoRequestDTO) {
//        ProdutoModel produtoModel = new ProdutoModel();
//        BeanUtils.copyProperties(produtoRequestDTO, produtoModel);
//        return produtoModel;
//    }
//
//    public ProdutoResponseDTO toResponse(ProdutoModel produtoModel) {
//        ProdutoResponseDTO produtoResponseDTO = new ProdutoResponseDTO();
//        BeanUtils.copyProperties(produtoModel, produtoResponseDTO);
//        return produtoResponseDTO;
//    }

    public ProdutoModel getById(Integer id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
    }

    public List<ProdutoModel> listarProdutos() {
        return produtoRepository.findAll();
    }

    public List<ProdutoResponseDTO> listarProdutosResponse() {
        return produtoRepository.findAll().stream()
                .map(p -> objectMapper.convertValue(p, ProdutoResponseDTO.class))
                .toList();
    }

    public ProdutoResponseDTO getByIdResponse(Integer id) {
        return objectMapper.convertValue(getById(id), ProdutoResponseDTO.class);
    }

    @Transactional
    public ProdutoResponseDTO salvarProduto(ProdutoRequestDTO produtoRequest) {
        ProdutoModel produtoModel = objectMapper.convertValue(produtoRequest, ProdutoModel.class);
        return objectMapper.convertValue(produtoRepository.save(produtoModel), ProdutoResponseDTO.class);
    }

    @Transactional
    public void excluirProduto(Integer id) {
        ProdutoModel produtoModel = this.getById(id);
        produtoRepository.delete(produtoModel);
    }

    @Transactional
    public void atualizarProduto(Integer id, ProdutoRequestDTO produtoRequestDTO) {
        ProdutoModel p = this.getById(id);
        BeanUtils.copyProperties(produtoRequestDTO, p);

        produtoRepository.save(p);
    }

    @Transactional
    public void alterarProduto(Integer id, ProdutoRequestDTO produtoRequestDTO) {
        ProdutoModel p = this.getById(id);

        if (produtoRequestDTO.getNome() != null) {
            p.setNome(produtoRequestDTO.getNome());
        }
        if (produtoRequestDTO.getPreco() != null) {
            p.setPreco(produtoRequestDTO.getPreco());
        }
        if (produtoRequestDTO.getQuantidadeEstoque() != null) {
            p.setQuantidadeEstoque(produtoRequestDTO.getQuantidadeEstoque());
        }
        if (produtoRequestDTO.getDescricao() != null) {
            p.setDescricao(produtoRequestDTO.getDescricao());
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
