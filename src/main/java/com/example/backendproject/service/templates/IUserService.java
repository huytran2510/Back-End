package com.example.backendproject.service.templates;

import com.example.backendproject.domain.dto.forcreate.CCustomer;
import com.example.backendproject.domain.dto.forlist.UserDTO;
import com.example.backendproject.domain.dto.forlist.UserListDTO;
import com.example.backendproject.domain.model.User;
import com.example.backendproject.domain.model.enums.ERole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface IUserService {
    User add(CCustomer cCustomer);
    User getUserById(Long id);
    void deleteUser(Long id);
    User updateUser(Long id, UserDTO userDTO, Set<ERole> roles);
    Page<UserListDTO> getAllUsers(int page, int size);

    long getTotalUsers();
    User createUser(UserDTO userDTO);
}
