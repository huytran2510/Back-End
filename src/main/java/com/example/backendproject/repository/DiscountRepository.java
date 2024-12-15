package com.example.backendproject.repository;

import com.example.backendproject.domain.dto.forlist.DiscountDTO;
import com.example.backendproject.domain.dto.forlist.ProductDTO;
import com.example.backendproject.domain.model.Discount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Optional<Discount> findByCouponCode(String String);

    @Query("SELECT new com.example.backendproject.domain.dto.forlist.DiscountDTO(" +
            "d.discountId, d.couponCode, d.discountDescription, d.discountPercent, d.startDate, d.endDate, d.minOrderAmount, d.discountType) " +
            "FROM Discount d " +
            "GROUP BY d.discountId, d.couponCode, d.discountDescription, d.discountPercent, d.startDate, d.endDate, d.minOrderAmount, d.discountType")
    Page<DiscountDTO> findAllDiscount(Pageable pageable);
}
