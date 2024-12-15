package com.example.backendproject.service;


import com.example.backendproject.domain.dto.forlist.OrdersByDayDTO;
import com.example.backendproject.domain.dto.forlist.RevenueByDayDTO;
import com.example.backendproject.domain.dto.forlist.WeeklyReportDTO;
import com.example.backendproject.domain.model.Order;
import com.example.backendproject.repository.OrderRepository;
import com.example.backendproject.service.templates.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements IReportService {
    @Autowired
    private OrderRepository orderRepository;

//    public List<OrderCountDTO> getOrderCountByWeek(LocalDate startDate, LocalDate endDate) {
//        return orderRepository.findOrderCountByWeek(startDate, endDate);
//    }
//
//    public List<RevenueDTO> getRevenueByWeek(LocalDate startDate, LocalDate endDate) {
//        return orderRepository.findRevenueByWeek(startDate, endDate);
//    }
//
//    public List<Object[]> getRevenueByDateRange(LocalDate startDate, LocalDate endDate) {
//        return orderRepository.findRevenueByDateRange(startDate, endDate);
//    }
//
//    public List<Object[]> getOrderCountByDateRange(LocalDate startDate, LocalDate endDate) {
//        return orderRepository.findOrderCountByDateRange(startDate, endDate);
//    }
//
//    public List<Object[]> getOrderCountByMonth(int month, int year) {
//        return orderRepository.findOrderCountByMonth(month, year);
//    }
//
//    public List<Object[]> getRevenueByMonth(int month, int year) {
//        return orderRepository.findRevenueByMonth(month, year);
//    }
//
//    public List<Object[]> getOrderCountForLast7Days(LocalDate startDate, LocalDate endDate) {
//        return orderRepository.findOrderCountForLast7Days(startDate, endDate);
//    }

    public Double getRevenue(LocalDate startDate, LocalDate endDate) {
        return orderRepository.findTotalRevenueBetween(startDate, endDate);
    }

    public Long getOrderCount(LocalDate startDate, LocalDate endDate) {
        return orderRepository.countOrdersBetween(startDate, endDate);
    }

    public List<Long> getWeeklyOrderCount(LocalDate startDate, LocalDate endDate) {
        List<Long> weeklyCounts = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            weeklyCounts.add(orderRepository.countOrdersByDayOfWeek(i, startDate, endDate));
        }
        return weeklyCounts;
    }

    public List<RevenueByDayDTO> getRevenueByDay(LocalDate startDate, LocalDate endDate) {
        // Truy vấn dữ liệu doanh thu theo ngày từ cơ sở dữ liệu
        return orderRepository.findRevenueByDay(startDate, endDate);
    }

    public List<OrdersByDayDTO> getOrdersByDay(LocalDate startDate, LocalDate endDate) {
        // Truy vấn số lượng đơn hàng theo ngày từ cơ sở dữ liệu
        return orderRepository.findOrdersByDay(startDate, endDate);
    }

    public List<WeeklyReportDTO> getWeeklyReport() {
        // Lấy ngày hôm nay
        LocalDate today = LocalDate.now();

        // Lấy ngày đầu tuần (Thứ 2)
        LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1);

        // Lấy ngày cuối tuần (Chủ Nhật)
        LocalDate endOfWeek = startOfWeek.plusDays(6); // Thứ Hai + 6 ngày = Chủ Nhật

        // Truy vấn dữ liệu cho 7 ngày trong tuần
        return orderRepository.findWeeklyReport(startOfWeek, endOfWeek);
    }
}
