package com.example.backendproject.controller;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Getter
    @Value("${PAY_URL}")
    private String vnp_PayUrl;
}
