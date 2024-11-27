package com.example.backendproject.controller;

import com.example.backendproject.domain.dto.forlist.ProductReviewDTO;
import com.example.backendproject.service.templates.IProductReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ProductReviewController {
    @Autowired
    private IProductReviewService productReviewService;

    // Lấy danh sách đánh giá theo sản phẩm
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductReviewDTO>> getReviewsByProductId(@PathVariable Long productId) {
        List<ProductReviewDTO> reviews = productReviewService.getReviewsByProductId(productId);
        return ResponseEntity.ok(reviews);
    }

    // Thêm đánh giá mới
    @PostMapping
    public ResponseEntity<ProductReviewDTO> addReview(
            @RequestParam Long productId,
            @RequestParam Long customerId,
            @RequestParam Integer rating,
            @RequestParam(required = false) String reviewText) {

        ProductReviewDTO review = productReviewService.addReview(productId, customerId, rating, reviewText);
        return ResponseEntity.ok(review);
    }
}
