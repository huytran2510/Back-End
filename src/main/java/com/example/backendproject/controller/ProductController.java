package com.example.backendproject.controller;



import com.example.backendproject.domain.dto.forlist.ProductDTO;
import com.example.backendproject.domain.model.Product;
import com.example.backendproject.service.templates.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private IProductService productService;

    @GetMapping("")
    public ResponseEntity<List<ProductDTO>> getAll() {
        List<ProductDTO> productList = productService.getAllProducts();
        System.out.println(productList);
        return ResponseEntity.ok(productList);
    }
}
