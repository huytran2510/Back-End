package com.example.backendproject.domain.dto.forlist;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RevenueByDayDTO {
    private LocalDate date;
    private Double totalRevenue;
}
