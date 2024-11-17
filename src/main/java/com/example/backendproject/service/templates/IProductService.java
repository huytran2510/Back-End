package com.example.backendproject.service.templates;

import com.example.backendproject.domain.dto.forlist.ProductDTO;
import com.example.backendproject.domain.dto.forlist.ProductDetailDTO;
import com.example.backendproject.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    List<ProductDTO> getAllProducts();

    //    List<Product> getProductByCategoryId(Long categoryId);
    ProductDetailDTO findProductById(Long id);
}
