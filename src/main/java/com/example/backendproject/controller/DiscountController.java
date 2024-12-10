package com.example.backendproject.controller;

import com.example.backendproject.domain.dto.request.CouponRequest;
import com.example.backendproject.domain.dto.response.DiscountValidationResult;
import com.example.backendproject.domain.dto.response.ErrorResponse;
import com.example.backendproject.domain.model.Discount;
import com.example.backendproject.service.templates.IDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/discount")
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
}
