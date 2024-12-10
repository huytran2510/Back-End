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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/place-order")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @PostMapping("")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest orderRequest, @AuthenticationPrincipal Customer customer) {
        if (orderRequest.getCartItems().isEmpty()) {
            return ResponseEntity.badRequest().body("Giỏ hàng trống, vui lòng thêm sản phẩm.");
        }

        try {
            // Gọi service để lưu đơn hàng
            Order order = orderService.saveOrder(orderRequest.getOrder(), orderRequest.getCartItems(), customer);
            // Trả về đơn hàng đã lưu
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã có lỗi xảy ra khi đặt hàng: " + e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders = orderService.getAllOrders(pageable);

        // Tạo cấu trúc dữ liệu JSON trả về
        Map<String, Object> response = new HashMap<>();
        response.put("data", orders.getContent()); // Danh sách các orders
        response.put("currentPage", orders.getNumber()); // Trang hiện tại
        response.put("totalItems", orders.getTotalElements()); // Tổng số phần tử
        response.put("totalPages", orders.getTotalPages()); // Tổng số trang

        return ResponseEntity.ok(response);
    }

    // Update an Order
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable String id, @RequestBody Order updatedOrder) {
        Order order = orderService.updateOrder(id, updatedOrder);
        if (order != null) {
            return ResponseEntity.ok(order);
        }
        return ResponseEntity.notFound().build();
    }
}
