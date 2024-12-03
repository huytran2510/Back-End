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
    private String size;
    private String topping;
    private String imgUrl;
    // Các getters và setters
}