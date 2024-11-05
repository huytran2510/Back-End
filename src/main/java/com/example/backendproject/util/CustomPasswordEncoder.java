package com.example.backendproject.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncoder {

    private PasswordEncoder passwordEncoder;

    public CustomPasswordEncoder() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}
