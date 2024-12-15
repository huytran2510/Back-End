package com.example.backendproject.domain.dto.forlist;

import com.example.backendproject.domain.model.Product;
import com.example.backendproject.domain.model.enums.DiscountType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDTO {
    private Long discountId;

    private String couponCode; // Mã khuyến mãi

    private String discountDescription;
    private Double discountPercent;
    private LocalDate startDate;
    private LocalDate endDate;

    private Double minOrderAmount;

    private DiscountType discountType;


}
