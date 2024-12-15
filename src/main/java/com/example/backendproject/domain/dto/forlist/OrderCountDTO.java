package com.example.backendproject.domain.dto.forlist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Setter
@Getter
@NoArgsConstructor
public class OrderCountDTO {

    private int week;
    private long orderCount;

    public OrderCountDTO(int week, long orderCount) {
        this.week = week;
        this.orderCount = orderCount;
    }
}
