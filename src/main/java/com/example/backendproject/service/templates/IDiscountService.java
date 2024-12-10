package com.example.backendproject.service.templates;

import com.example.backendproject.domain.dto.forcreate.CartItem;
import com.example.backendproject.domain.dto.response.DiscountValidationResult;
import com.example.backendproject.domain.model.Discount;

import java.util.List;

public interface IDiscountService {
    DiscountValidationResult validateCoupon(String couponCode, List<CartItem> cartItems, Double totalAmount);
    List<Discount> getAllCoupons();
}
