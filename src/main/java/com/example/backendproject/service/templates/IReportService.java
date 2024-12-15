package com.example.backendproject.service.templates;


import com.example.backendproject.domain.dto.forlist.OrdersByDayDTO;
import com.example.backendproject.domain.dto.forlist.RevenueByDayDTO;
import com.example.backendproject.domain.dto.forlist.WeeklyReportDTO;
import com.example.backendproject.domain.model.Order;

import java.time.LocalDate;
import java.util.List;

public interface IReportService {


//    List<Order> getOrdersBetweenDates(LocalDate startDate, LocalDate endDate);
//    Double getTotalRevenueBetweenDates(LocalDate startDate, LocalDate endDate);
//
//    Long countOrdersByDayOfWeek(int dayOfWeek);
    Double getRevenue(LocalDate startDate, LocalDate endDate);
    Long getOrderCount(LocalDate startDate, LocalDate endDate);
    List<Long> getWeeklyOrderCount(LocalDate startDate, LocalDate endDate);
    List<RevenueByDayDTO> getRevenueByDay(LocalDate startDate, LocalDate endDate);
    List<OrdersByDayDTO> getOrdersByDay(LocalDate startDate, LocalDate endDate);

    List<WeeklyReportDTO> getWeeklyReport();
}
