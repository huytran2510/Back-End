package com.example.backendproject.service.templates;

import com.example.backendproject.domain.dto.forcreate.COrder;
import com.example.backendproject.domain.dto.forcreate.CartItem;
import com.example.backendproject.domain.model.Customer;
import com.example.backendproject.domain.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderService {
    Order saveOrder(COrder cOrder, List<CartItem> itemList, Customer customer);
    Page<Order> getAllOrders(Pageable pageable);
    Order updateOrder(String id, Order updatedOrder);
}
