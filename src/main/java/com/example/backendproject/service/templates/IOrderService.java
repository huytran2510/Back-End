package com.example.backendproject.service.templates;

import com.example.backendproject.domain.dto.forcreate.COrder;
import com.example.backendproject.domain.dto.forcreate.CartItem;
import com.example.backendproject.domain.model.Order;

import java.util.List;

public interface IOrderService {
    Order saveOrder(COrder cOrder, List<CartItem> itemList);
}
