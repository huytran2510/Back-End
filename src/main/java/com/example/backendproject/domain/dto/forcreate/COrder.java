package com.example.backendproject.domain.dto.forcreate;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class COrder {
    private String orderId;
    private String shipAddress;
    private String firstName;
    private String lastName;
    private String shipPhone;
    private LocalDate shippedDate;
    private String email;
    private double priceDiscount;
    private double discount;
    private String note;
}
