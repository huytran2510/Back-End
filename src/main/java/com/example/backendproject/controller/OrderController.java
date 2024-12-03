package com.example.backendproject.controller;


import com.example.backendproject.domain.dto.forcreate.COrder;
import com.example.backendproject.domain.dto.forcreate.CartItem;
import com.example.backendproject.domain.dto.forcreate.OrderRequest;
import com.example.backendproject.domain.model.Customer;
import com.example.backendproject.domain.model.Order;
import com.example.backendproject.service.templates.IOrderService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/place-order")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @PostMapping("")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest orderRequest, @AuthenticationPrincipal Customer customer) {
        // Kiểm tra nếu giỏ hàng trống
        if (orderRequest.getCartItems().isEmpty()) {
            return ResponseEntity.badRequest().body("Giỏ hàng trống, vui lòng thêm sản phẩm.");
        }

        try {
            // Gọi service để lưu đơn hàng
            Order order = orderService.saveOrder(orderRequest.getCOrder(), orderRequest.getCartItems(), customer);

            // Trả về đơn hàng đã lưu
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã có lỗi xảy ra khi đặt hàng: " + e.getMessage());
        }
    }
}
