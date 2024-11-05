package com.example.backendproject.domain.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthCredentialRequest {
    private String username;
    private String password;
}
