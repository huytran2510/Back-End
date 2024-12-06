package com.example.backendproject.service.templates;

import com.example.backendproject.domain.dto.forlist.ProductDTO;
import com.example.backendproject.domain.dto.forlist.ProductDetailDTO;
import com.example.backendproject.domain.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    long getTotalProducts();
    Page<ProductDTO> getAllProductsPage(int page, int size);

    List<ProductDTO> getAllProducts();

    ProductDetailDTO findProductById(Long id);

    List<ProductDTO> listProductByCategoryId(Long id);

    Optional<Product> getProductById(Long id);
    Product createProduct(Product product);
    Optional<Product> updateProduct(Long id, Product updatedProduct);
    boolean deleteProduct(Long id);
}
