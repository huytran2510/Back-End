package com.example.backendproject.domain.dto.forlist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyReportDTO {
    private LocalDate date;
    private double revenue;
    private long orders;
}
