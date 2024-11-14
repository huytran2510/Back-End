package com.example.backendproject.service.templates;

import java.util.List;

public interface IEmailService {
    void sendSuccessRegister(String toEmail, String newPassword);
//    void sendOrderConfirmationEmail(String toEmail, String orderId, String customerName, String phone, List<CartItem> itemList);
}
