package com.example.backendproject.service;

import com.example.backendproject.domain.dto.forlist.ProductDTO;
import com.example.backendproject.domain.dto.forlist.ProductDetailDTO;
import com.example.backendproject.domain.model.Product;
import com.example.backendproject.domain.model.ProductImages;
import com.example.backendproject.repository.ProductImagesRepository;
import com.example.backendproject.repository.ProductRepository;
import com.example.backendproject.service.templates.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImagesRepository productImagesRepository;
//    public List<ProductDTO> getAllProducts() {
//        return productRepository.findAllProducts();
//    }

//    @Override
//    public Product findById(Long id) { return productRepository.findProductByProductId(id);}

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAllProducts();
    }

    @Override
    public ProductDetailDTO findProductById(Long id) {
        // Fetch product with category and images
        Product product = productRepository.findByIdWithCategoryAndImages(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + id + " not found"));

        // Convert to DTO
        return toProductDetailDTO(product);
    }

    private ProductDetailDTO toProductDetailDTO(Product product) {
        List<String> urlImages = product.getProductImages()
                .stream()
                .map(ProductImages::getImageUrl) // Assuming ProductImage has `getUrl` method
                .collect(Collectors.toList());

        return new ProductDetailDTO(
                product.getProductId(),
                product.getProductName(),
                product.getUnitPrice(),
                product.getUnitsInStock(),
                product.getDiscontinued(),
                product.getCategory() != null ? product.getCategory().getId() : null,
                urlImages
        );
    }
}
