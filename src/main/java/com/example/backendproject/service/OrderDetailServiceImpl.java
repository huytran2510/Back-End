package com.example.backendproject.service;


import com.example.backendproject.domain.dto.forcreate.CartItem;
import com.example.backendproject.domain.model.Order;
import com.example.backendproject.domain.model.OrderDetail;
import com.example.backendproject.domain.model.Product;
import com.example.backendproject.repository.OrderDetailRepository;
import com.example.backendproject.repository.OrderRepository;
import com.example.backendproject.repository.ProductRepository;
import com.example.backendproject.service.templates.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderDetailServiceImpl implements IOrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public void save(CartItem item, Order order) {
        Product product = productRepository.findProductByProductId(item.getProductId());
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
//        orderDetail.setDiscount(product.getDiscount().getDiscountPercent());
        orderDetail.setQuantity(item.getQuantity());
        orderDetail.setCost(item.getPrice());
        orderDetail.setUnitPrice(item.getPriceDiscount());
        orderDetailRepository.save(orderDetail);
    }

    public List<OrderDetail> findAllByOrderId(String orderId){
        return orderDetailRepository.findAllByOrderId(orderId);
    }
}
