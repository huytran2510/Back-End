package com.example.backendproject.domain.dto.forlist;

import com.example.backendproject.domain.model.Authority;
import com.example.backendproject.domain.model.enums.ERole;
import com.example.backendproject.domain.model.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
public class UserListDTO {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;

    public UserListDTO(Long id, String username, String email, String phone, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
