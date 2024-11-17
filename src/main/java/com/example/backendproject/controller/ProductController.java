package com.example.backendproject.controller;



import com.example.backendproject.domain.dto.forlist.ProductDTO;
import com.example.backendproject.domain.dto.forlist.ProductDetailDTO;
import com.example.backendproject.domain.model.Product;
import com.example.backendproject.service.templates.IProductService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public ResponseEntity<?> findProductById(@PathVariable Long id) {
        // Assuming you have a service to fetch the product by ID
        ProductDetailDTO productDetail = productService.findProductById(id);

        if (productDetail  != null) {
            return ResponseEntity.ok(productDetail );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product not found with ID: " + id);
        }
    }
}
