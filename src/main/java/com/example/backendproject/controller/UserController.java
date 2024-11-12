package com.example.backendproject.controller;

import com.example.backendproject.domain.dto.forcreate.CCustomer;
import com.example.backendproject.domain.model.User;
import com.example.backendproject.service.templates.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registration(@RequestBody CCustomer customer) {
        User user = userService.add(customer);
        return ResponseEntity.ok(user);
    }
}
