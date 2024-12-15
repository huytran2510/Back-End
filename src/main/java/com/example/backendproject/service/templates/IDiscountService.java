package com.example.backendproject.service.templates;

import com.example.backendproject.domain.dto.forcreate.CartItem;
import com.example.backendproject.domain.dto.forlist.DiscountDTO;
import com.example.backendproject.domain.dto.response.DiscountValidationResult;
import com.example.backendproject.domain.model.Discount;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IDiscountService {
    DiscountValidationResult validateCoupon(String couponCode, List<CartItem> cartItems, Double totalAmount);
    List<Discount> getAllCoupons();
    Page<DiscountDTO> getAllDiscount(int page, int size);
    long getTotalDiscounts();
    void deleteDiscount(Long id);
    Discount updateDiscount(Long id, Discount updatedDiscount);
    Discount createDiscount(Discount discount);
    Optional<Discount> getDiscountById(Long id);
}
