package com.example.backendproject.service;

import com.example.backendproject.domain.model.Customer;
import com.example.backendproject.domain.model.User;
import com.example.backendproject.repository.CustomerRepository;
import com.example.backendproject.repository.UserRepository;
import com.example.backendproject.util.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CustomPasswordEncoder passwordEncoder;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Customer> customerOptional = customerRepository.findByUsername(username);
        if (customerOptional.isPresent()) {
            System.out.println("Found in Customer: " + username);
            return customerOptional.get();
        }

        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            System.out.println("Found in User: " + username);
            return userOptional.get();
        }

        System.out.println("Username not found: " + username);
        throw new UsernameNotFoundException("Invalid credentials");
    }
}
