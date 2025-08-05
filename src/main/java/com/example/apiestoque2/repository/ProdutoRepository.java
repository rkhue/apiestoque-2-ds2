package com.example.apiestoque2.repository;

import com.example.apiestoque2.model.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<ProdutoModel, Integer> {
}
