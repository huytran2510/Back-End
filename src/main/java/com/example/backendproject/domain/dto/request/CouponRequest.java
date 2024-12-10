package com.example.backendproject.domain.dto.request;

import com.example.backendproject.domain.dto.forcreate.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CouponRequest {
    private String couponCode; // Mã khuyến mãi người dùng nhập
    private List<CartItem> cartItems; // Danh sách sản phẩm trong giỏ hàng
    private Double totalAmount;
}
