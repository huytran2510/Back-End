package com.example.backendproject.domain.dto.forlist;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
//@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long productId;
    private String productName;
    private Double unitPrice;
    private Integer unitsInStock;
    private String description;
    private Boolean discontinued;
    private Long categoryId;
    private String urlImage;
    public ProductDTO(Long productId, String productName, Double unitPrice, Integer unitsInStock, Boolean discontinued, Long categoryId, String description, String urlImage) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.unitsInStock = unitsInStock;
        this.discontinued = discontinued;
        this.categoryId = categoryId;
        this.urlImage = urlImage;
        this.description = description;
    }
}
