package com.example.backendproject.controller;



import com.example.backendproject.domain.dto.forlist.ProductDTO;
import com.example.backendproject.domain.dto.forlist.ProductDetailDTO;
import com.example.backendproject.domain.model.Category;
import com.example.backendproject.domain.model.Product;
import com.example.backendproject.domain.model.ProductImages;
import com.example.backendproject.service.templates.ICategoryService;
import com.example.backendproject.service.templates.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.storage.Storage;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.cloud.StorageClient;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@RestController
@RequestMapping("/api/products")
@CrossOrigin("http://localhost:3000/")
public class ProductController {
    @Autowired
    private IProductService productService;

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Autowired
    private ICategoryService categoryService;

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
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
    private final ObjectMapper objectMapper = new ObjectMapper();  // Dùng ObjectMapper để chuyển JSON

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Product> createProduct(
            @RequestParam("productDTO") String productDTOJson,
            @RequestParam("images") MultipartFile[] imageFiles
    ) {
        try {
            // Chuyển chuỗi JSON thành đối tượng ProductDTO
            ProductDTO productDTO = objectMapper.readValue(productDTOJson, ProductDTO.class);

            if (productDTO.getProductId() != null) {
                return ResponseEntity.badRequest().body(null);
            }

            // Tạo đối tượng sản phẩm
            Product product = new Product();
            product.setProductName(productDTO.getProductName());
            product.setUnitPrice(productDTO.getUnitPrice());
            product.setUnitsInStock(productDTO.getUnitsInStock());
            product.setDiscontinued(productDTO.getDiscontinued());
            product.setCategory(categoryService.findById(productDTO.getCategoryId()).get());
            product.setDescription(productDTO.getDescription());

            // Xử lý các file ảnh và tạo danh sách ProductImages
            Set<ProductImages> productImages = new HashSet<>();
            for (MultipartFile imageFile : imageFiles) {
                String fileName = saveFileToLocalDirectory(imageFile); // Giả sử bạn có phương thức lưu file
                if (fileName != null) {
                    ProductImages productImage = new ProductImages();
                    productImage.setImageUrl(fileName);
                    if (productImage.getProducts() == null) {
                        productImage.setProducts(new HashSet<>());
                    }

                    // Thêm Product vào ProductImages
                    productImage.getProducts().add(product);
                    productImages.add(productImage);
                }
            }

            // Gắn danh sách hình ảnh vào sản phẩm
            product.setProductImages(productImages);

            // Lưu sản phẩm
            Product createdProduct = productService.createProduct(product);

            return ResponseEntity.ok(createdProduct);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
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

    @GetMapping("/export-csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        // Đặt Content Type và header cho file CSV
        response.setContentType("text/csv;charset=UTF-8");  // Đảm bảo thêm charset=UTF-8
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=products.csv";
        response.setHeader(headerKey, headerValue);

        // Tạo PrintWriter với mã hóa UTF-8
        OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8);
        PrintWriter csvWriter = new PrintWriter(writer);

        // Ghi header cho CSV
        csvWriter.write("Product ID,Product Name,Unit Price,Units In Stock,Description\n");

        List<ProductDTO> products = productService.getAllProducts(); // Lấy danh sách sản phẩm từ database

        // Ghi dữ liệu vào CSV
        for (ProductDTO product : products) {
            csvWriter.write(String.format(
                    "\"%s\",\"%s\",%.2f,%d,\"%s\"\n",  // Dùng dấu nháy kép để bảo vệ dữ liệu có dấu phẩy
                    product.getProductId(),
                    product.getProductName(),
                    product.getUnitPrice(),
                    product.getUnitsInStock(),
                    product.getDescription()
            ));
        }

        csvWriter.flush();  // Đảm bảo ghi ra hết dữ liệu
        csvWriter.close();  // Đóng writer
    }


    private String saveFileToLocalDirectory(MultipartFile file) {
        try {
            // Kiểm tra nếu file trống
            if (file == null || file.isEmpty()) {
                throw new IllegalArgumentException("File is empty or null");
            }

            // Chuẩn hóa tên file
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String fileName = UUID.randomUUID() + "_" + originalFileName;

            // Kiểm tra kiểu file
            String contentType = file.getContentType();
            if (!Arrays.asList("image/jpeg", "image/png", "image/gif").contains(contentType)) {
                throw new IllegalArgumentException("Unsupported file type: " + contentType);
            }

            // Đường dẫn lưu file
            Path filePath = Paths.get(uploadDir, fileName);

            // Tạo thư mục nếu chưa tồn tại
            Files.createDirectories(filePath.getParent());

            // Lưu file vào thư mục
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Trả về đường dẫn URL tương đối (để sử dụng trong frontend)
            return "/img/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Error saving file to local directory", e);
        }
    }






}
