package com.example.backendproject.service;

import com.example.backendproject.domain.dto.forlist.ProductDTO;
import com.example.backendproject.domain.model.Product;
import com.example.backendproject.repository.ProductImagesRepository;
import com.example.backendproject.repository.ProductRepository;
import com.example.backendproject.service.templates.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImagesRepository productImagesRepository;
//    public List<ProductDTO> getAllProducts() {
//        return productRepository.findAllProducts();
//    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAllProducts();
    }
}
