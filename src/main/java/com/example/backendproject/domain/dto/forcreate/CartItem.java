package com.example.backendproject.domain.dto.forcreate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItem {
    private Long productId;
    private String productName;
    private int quantity;
    private double price;
    private double priceDiscount;
    private double discount;

    private String imgUrl;
    // Các getters và setters
}