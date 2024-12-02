package com.example.backendproject.domain.dto.forcreate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class COrder {
    private String orderId;
    private String shipAddress;
    private String firstName;
    private String lastName;
    private String shipPhone;
    private LocalDate shippedDate;
    private String email;
    private Long customerId;
}
