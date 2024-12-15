package com.example.backendproject.controller;

import com.example.backendproject.domain.dto.forlist.DiscountDTO;
import com.example.backendproject.domain.dto.forlist.ProductDTO;
import com.example.backendproject.domain.dto.request.CouponRequest;
import com.example.backendproject.domain.dto.response.DiscountValidationResult;
import com.example.backendproject.domain.dto.response.ErrorResponse;
import com.example.backendproject.domain.model.Discount;
import com.example.backendproject.service.templates.IDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/discount")
@CrossOrigin("http://localhost:3000/")
public class DiscountController {

    @Autowired
    private IDiscountService discountService;
    @PostMapping("/validate")
    public ResponseEntity<?> validateCoupon(@RequestBody CouponRequest couponRequest) {
        try {
            DiscountValidationResult result = discountService.validateCoupon(
                    couponRequest.getCouponCode(),
                    couponRequest.getCartItems(),
                    couponRequest.getTotalAmount()
            );
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi kiểm tra mã khuyến mãi: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getCoupons() {
        try {
            List<Discount> coupons = discountService.getAllCoupons();
            return ResponseEntity.ok(coupons);
        } catch (Exception e) {
            // Trả về đối tượng lỗi với thông báo lỗi
            ErrorResponse errorResponse = new ErrorResponse("Lỗi khi lấy danh sách mã khuyến mãi: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<DiscountDTO> productList = discountService.getAllDiscount(page, size);
            long totalProducts = discountService.getTotalDiscounts();

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
    public ResponseEntity<?> getDiscountById(@PathVariable Long id) {
        try {
            Discount discount = discountService.getDiscountById(id)
                    .orElseThrow(() -> new RuntimeException("Discount not found with id: " + id));
            return ResponseEntity.ok(discount);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving the discount.");
        }
    }

    @PostMapping
    public ResponseEntity<?> createDiscount(@RequestBody DiscountDTO discountDTO) {
        try {
            Discount discount = new Discount();
            discount.setCouponCode(discountDTO.getCouponCode());
            discount.setDiscountDescription(discountDTO.getDiscountDescription());
            discount.setDiscountPercent(discountDTO.getDiscountPercent());
            discount.setStartDate(discountDTO.getStartDate());
            discount.setEndDate(discountDTO.getEndDate());
            discount.setMinOrderAmount(discountDTO.getMinOrderAmount());
            discount.setDiscountType(discountDTO.getDiscountType());
            Discount createdDiscount = discountService.createDiscount(discount);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDiscount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to create discount: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDiscount(@PathVariable Long id, @RequestBody DiscountDTO discountDTO) {
        try {
            Discount updatedDiscount = new Discount();
            updatedDiscount.setCouponCode(discountDTO.getCouponCode());
            updatedDiscount.setDiscountDescription(discountDTO.getDiscountDescription());
            updatedDiscount.setDiscountPercent(discountDTO.getDiscountPercent());
            updatedDiscount.setStartDate(discountDTO.getStartDate());
            updatedDiscount.setEndDate(discountDTO.getEndDate());
            updatedDiscount.setMinOrderAmount(discountDTO.getMinOrderAmount());
            updatedDiscount.setDiscountType(discountDTO.getDiscountType());

            Discount result = discountService.updateDiscount(id, updatedDiscount);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update discount.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDiscount(@PathVariable Long id) {
        try {
            discountService.deleteDiscount(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete discount.");
        }
    }
}
