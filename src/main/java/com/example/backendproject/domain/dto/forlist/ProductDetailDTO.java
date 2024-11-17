package com.example.backendproject.domain.dto.forlist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailDTO {
    private Long productId;
    private String productName;
    private Double unitPrice;
    private Integer unitsInStock;
    private Boolean discontinued;
    private Long categoryId;
    private List<String> urlImage;
}
