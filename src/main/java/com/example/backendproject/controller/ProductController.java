package com.example.backendproject.controller;



import com.example.backendproject.domain.dto.forlist.ProductDTO;
import com.example.backendproject.domain.dto.forlist.ProductDetailDTO;
import com.example.backendproject.domain.model.Category;
import com.example.backendproject.domain.model.Product;
import com.example.backendproject.domain.model.ProductImages;
import com.example.backendproject.service.templates.IProductService;
import com.google.api.services.storage.Storage;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.cloud.StorageClient;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

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


    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<ProductDTO> productList = productService.getAllProductsPage(page, size);
            long totalProducts = productService.getTotalProducts();

            Map<String, Object> response = new HashMap<>();
            response.put("data", productList.getContent()); // Lấy danh sách sản phẩm
            response.put("total", totalProducts);
            response.put("currentPage", productList.getNumber());
            response.put("totalPages", productList.getTotalPages());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
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

    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductDTO>> getAllByCategoryId(@PathVariable Long id) {
        List<ProductDTO> productList = productService.listProductByCategoryId(id);
        System.out.println(productList);
        return ResponseEntity.ok(productList);
    }


    @GetMapping("/test/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Thêm sản phẩm mới
    @PostMapping
    public ResponseEntity<Product> createProduct(
            @RequestBody ProductDTO productDTO,  // Dữ liệu sản phẩm được gửi dưới dạng JSON
            @RequestParam("images") MultipartFile[] imageFiles  // Các ảnh được gửi qua file
    ) {
        // Kiểm tra nếu sản phẩm đã có ID (trong trường hợp gửi yêu cầu cập nhật thay vì tạo mới)
        if (productDTO.getProductId() != null) {
            return ResponseEntity.badRequest().body(null);
        }

        // Tạo đối tượng Product từ ProductDTO
        Product product = new Product();
        product.setProductName(productDTO.getProductName());
        product.setUnitPrice(productDTO.getUnitPrice());
        product.setUnitsInStock(productDTO.getUnitsInStock());
        product.setDiscontinued(productDTO.getDiscontinued());
//        product.setCategory((productDTO.getCategoryId()));

        // Upload hình ảnh lên Firebase Storage
        Set<ProductImages> productImages = new HashSet<>();
        for (MultipartFile imageFile : imageFiles) {
            String imageUrl = uploadFileToFirebase(imageFile);
            if (imageUrl != null) {
                ProductImages productImage = new ProductImages();
                productImage.setImageUrl(imageUrl);
                productImage.setProduct((Set<Product>) product);  // Gắn ảnh vào sản phẩm
                productImages.add(productImage);
            }
        }

        // Gắn danh sách ảnh vào sản phẩm
        product.setProductImages(productImages);

        // Lưu sản phẩm vào database
        Product createdProduct = productService.createProduct(product);

        return ResponseEntity.ok(createdProduct);
    }



    public String uploadFileToFirebase(MultipartFile file) {
        try {
            // Lấy bucket từ Firebase
            Bucket bucket = StorageClient.getInstance().bucket();

            // Tạo đường dẫn cho file
            String fileName = "uploads/" + file.getOriginalFilename();

            // Upload file lên Firebase Storage
            Blob blob = bucket.create(fileName, file.getInputStream(), file.getContentType());

            // Trả về URL của file đã upload
            return blob.getMediaLink();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    // Cập nhật sản phẩm theo ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestBody Product updatedProduct) {
        try {
            // Gọi service để cập nhật product
            return productService.updateProduct(id, updatedProduct)
                    .map(product -> {
                        // Thành công: trả về product
                        return ResponseEntity.ok(product);
                    })
                    .orElseGet(() -> {
                        // Không tìm thấy: trả về lỗi 404
                        Map<String, String> errorResponse = new HashMap<>();
                        errorResponse.put("error", "Product not found");
                        errorResponse.put("id", String.valueOf(id));
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body((Product) errorResponse);
                    });
        } catch (Exception e) {
            // Xử lý lỗi chung: trả về lỗi 500 với thông tin chi tiết
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Internal Server Error");
            errorResponse.put("message", e.getMessage());
            e.printStackTrace(); // Log chi tiết lỗi ra console
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    // Xóa sản phẩm theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}
