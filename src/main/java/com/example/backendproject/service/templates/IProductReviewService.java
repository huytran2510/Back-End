package com.example.backendproject.service.templates;

import com.example.backendproject.domain.dto.forlist.ProductReviewDTO;

import java.util.List;

public interface IProductReviewService {
    List<ProductReviewDTO> getReviewsByProductId(Long productId);
    ProductReviewDTO addReview(Long productId, Long customerId, Integer rating, String reviewText);

}
