package com.example.backendproject.service.templates;


import com.example.backendproject.domain.dto.forcreate.CartItem;
import com.example.backendproject.domain.model.Order;
import com.example.backendproject.domain.model.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    void save(CartItem item, Order order);
    List<OrderDetail> findAllByOrderId(String orderId);
}
