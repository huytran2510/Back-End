package com.example.backendproject.service;

import com.example.backendproject.domain.dto.forcreate.COrder;
import com.example.backendproject.domain.dto.forcreate.CartItem;
import com.example.backendproject.domain.model.Customer;
import com.example.backendproject.domain.model.DeliveryStatus;
import com.example.backendproject.domain.model.Order;
import com.example.backendproject.repository.CustomerRepository;
import com.example.backendproject.repository.DeliveryStatusRepository;
import com.example.backendproject.repository.OrderRepository;
import com.example.backendproject.service.templates.IEmailService;
import com.example.backendproject.service.templates.IOrderDetailService;
import com.example.backendproject.service.templates.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private IOrderDetailService orderDetailService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DeliveryStatusRepository deliveryStatusRepository;

    @Autowired
    private IEmailService emailService;

    @Override
    public Order saveOrder(COrder cOrder, List<CartItem> itemList, Customer customer) {
        double totalPrice = 0;
        Order order = new Order();
        order.setOrderId(cOrder.getOrderId());
        order.setOrderDate(LocalDate.now());
        order.setRequiredDate(LocalDate.now());
        order.setShipAddress(cOrder.getShipAddress());
        order.setShipName(cOrder.getFirstName() + "" + cOrder.getLastName());
        order.setShipPhone(cOrder.getShipPhone());
        order.setTotalPayment(totalPrice);
        order.setShippedDate(LocalDate.now());
        order.setOrderId(createOrderId());
        order.setCustomer(customer);

        order.setEmail(cOrder.getEmail());
        // Save the entity to the database
        orderRepository.save(order);

        for (CartItem item : itemList) {
            orderDetailService.save(item, order);
        }
        System.out.println(cOrder.getEmail());
        emailService.sendOrderConfirmationEmail(cOrder.getEmail(), order.getOrderId(), order.getShipName(), order.getShipPhone(), itemList);
        return order;
    }

    public String createOrderId() {
        // Định dạng ngày giờ theo kiểu bạn muốn, ví dụ: "yyyyMMddHHmmss"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return "ORDER" + LocalDateTime.now().format(formatter);
    }

}
