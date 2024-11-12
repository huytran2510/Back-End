package com.example.backendproject.service.templates;

import com.example.backendproject.domain.model.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAll();
    List<Product> getProductByCategoryId(Long categoryId);
}
