package com.example.backendproject.service;

import com.example.backendproject.domain.model.Product;
import com.example.backendproject.repository.ProductRepository;
import com.example.backendproject.service.templates.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductByCategoryId(Long categoryId) {
        return productRepository.findAllByCategoryId(categoryId);
    }
}
