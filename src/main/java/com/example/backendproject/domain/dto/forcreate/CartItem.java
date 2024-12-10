package com.example.backendproject.domain.dto.forcreate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    private List<String> imgUrl;
    // Các getters và setters
}