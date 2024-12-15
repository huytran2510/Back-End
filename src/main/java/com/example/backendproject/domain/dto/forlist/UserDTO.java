package com.example.backendproject.domain.dto.forlist;

import com.example.backendproject.domain.model.Authority;
import com.example.backendproject.domain.model.enums.ERole;
import com.example.backendproject.domain.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.management.relation.Role;
import java.time.LocalDate;
import java.util.Set;

@Data
@Getter
@Setter
public class UserDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Gender gender;
    private LocalDate birthday;
    private ERole roles;



}

