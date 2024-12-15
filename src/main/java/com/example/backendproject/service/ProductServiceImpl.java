package com.example.backendproject.service;

import com.example.backendproject.domain.dto.forlist.ProductDTO;
import com.example.backendproject.domain.dto.forlist.ProductDetailDTO;
import com.example.backendproject.domain.model.Product;
import com.example.backendproject.domain.model.ProductImages;
import com.example.backendproject.repository.ProductImagesRepository;
import com.example.backendproject.repository.ProductRepository;
import com.example.backendproject.service.templates.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAllProducts();
    }

    @Override
    public Page<ProductDTO> getAllProductsPage(int page, int size) {
        return productRepository.findAllProducts(PageRequest.of(page, size));
    }



    public long getTotalProducts() {
        return productRepository.count(); // Lấy tổng số sản phẩm
    }

    @Override
    public ProductDetailDTO findProductById(Long id) {
        // Fetch product with category and images
        Product product = productRepository.findByIdWithCategoryAndImages(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + id + " not found"));

        // Convert to DTO
        return toProductDetailDTO(product);
    }

    @Override
    public List<ProductDTO> listProductByCategoryId(Long id) {
        return productRepository.findAllProductsByCategoryId(id);
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
                product.getDescription(),
                urlImages
        );
    }

    // Lấy thông tin sản phẩm theo ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Thêm sản phẩm mới
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // Cập nhật sản phẩm
    public Optional<Product> updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id).map(existingProduct -> {
            existingProduct.setProductName(updatedProduct.getProductName());
            existingProduct.setUnitPrice(updatedProduct.getUnitPrice());
            existingProduct.setUnitsInStock(updatedProduct.getUnitsInStock());
            existingProduct.setDiscontinued(updatedProduct.getDiscontinued());
            return productRepository.save(existingProduct);
        });
    }

    // Xóa sản phẩm theo ID
    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
