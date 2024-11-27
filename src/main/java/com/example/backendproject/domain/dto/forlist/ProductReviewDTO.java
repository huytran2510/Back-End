package com.example.backendproject.domain.dto.forlist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewDTO {
    private Integer reviewId;
    private Long productId;
    private Long customerId;
    private Integer rating;
    private String reviewText;
    private String customerName;
    private LocalDateTime reviewDate;
}
