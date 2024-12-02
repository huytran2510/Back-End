package com.example.backendproject.service.templates;

import com.example.backendproject.domain.dto.forcreate.CartItem;

import java.util.List;

public interface IEmailService {
    void sendSuccessRegister(String toEmail, String newPassword);
    void sendOrderConfirmationEmail(String toEmail, String orderId, String customerName, String phone, List<CartItem> itemList);
}
