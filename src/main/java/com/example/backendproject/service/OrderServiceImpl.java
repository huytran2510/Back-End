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
//        for(CartItem item : itemList) {
//            totalPrice += (item.getPriceDiscount()*item.getQuantity());
//        }
        Order order = new Order();
        order.setOrderId(cOrder.getOrderId());
        order.setOrderDate(LocalDate.now());
        order.setRequiredDate(LocalDate.now());
        order.setShipAddress(cOrder.getShipAddress());
        order.setShipName(cOrder.getFirstName() + "" + cOrder.getLastName());
        order.setShipPhone(cOrder.getShipPhone());
        order.setShippedDate(cOrder.getShippedDate());
        order.setTotalPayment(totalPrice);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
//        if (!username.equals("anonymousUser")) {
//            Optional<Customer> customer = customerRepository.findByUsername(username);
//            order.setCustomer(customer.get());
//        }
        order.setCustomer(customer);
//        DeliveryStatus deliveryStatus = deliveryStatusRepository.findById(1L)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid status ID"));
//        order.setDeliveryStatus(deliveryStatus);

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

}
