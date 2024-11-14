package com.example.backendproject.service.templates;

import com.example.backendproject.domain.dto.forcreate.CCustomer;
import com.example.backendproject.domain.model.User;

public interface IUserService {
    User add(CCustomer cCustomer);
}
