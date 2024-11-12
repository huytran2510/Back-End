package com.example.backendproject.service;

import com.example.backendproject.domain.dto.forcreate.CCustomer;
import com.example.backendproject.domain.model.User;
import com.example.backendproject.repository.UserRepository;
import com.example.backendproject.service.templates.IUserService;
import com.example.backendproject.util.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomPasswordEncoder passwordEncoder;

    @Autowired
    private EmailServiceImpl emailService;

    @Override
    public User add(CCustomer cCustomer) {
        User customer = new User();
        customer.setUsername(cCustomer.getUsername());
        customer.setPassword(passwordEncoder.getPasswordEncoder().encode(cCustomer.getPassword()));
        customer.setFirstName(cCustomer.getFirstName());
        customer.setLastName(cCustomer.getLastName());
//        customer.setAddress(cCustomer.getAddress());
        customer.setEmail(cCustomer.getEmail());
        customer.setBirthday(cCustomer.getBirthday());
        customer.setPhone(cCustomer.getPhone());
        customer.setGender((cCustomer.getGender()));
        emailService.sendSuccessRegister(cCustomer.getEmail(), cCustomer.getPassword());
        userRepository.save(customer);
        return customer;
    }
}
