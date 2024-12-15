package com.example.backendproject.domain.dto.forlist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrdersByDayDTO {

    private LocalDate date;
    private Long totalOrders;
}
