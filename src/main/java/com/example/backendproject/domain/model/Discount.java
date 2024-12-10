package com.example.backendproject.domain.model;

import com.example.backendproject.domain.model.enums.DiscountType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discountId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Product> products;

    @Column(unique = true, nullable = false)
    private String couponCode; // Mã khuyến mãi

    private String discountDescription;
    private Double discountPercent;
    private LocalDate startDate;
    private LocalDate endDate;

    private Double minOrderAmount;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

}
