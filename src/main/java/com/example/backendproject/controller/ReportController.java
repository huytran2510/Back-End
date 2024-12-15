package com.example.backendproject.controller;

import com.example.backendproject.domain.dto.forlist.*;
import com.example.backendproject.domain.model.Order;
import com.example.backendproject.repository.OrderRepository;
import com.example.backendproject.service.templates.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private IReportService reportService;


//    @GetMapping("/monthly")
//    public ResponseEntity<List<MonthlyReport>> getMonthlyReport() {
//        List<MonthlyReport> reports = orderRepository.getMonthlyReport();
//        return ResponseEntity.ok(reports);
//    }

//    @GetMapping("/revenue")
//    public List<Object[]> getRevenueByDateRange(
//            @RequestParam("startDate") LocalDate startDate,
//            @RequestParam("endDate") LocalDate endDate) {
//        return reportService.getRevenueByDateRange(startDate, endDate);
//    }
//
//    @GetMapping("/order-count")
//    public List<Object[]> getOrderCountByDateRange(
//            @RequestParam("startDate") LocalDate startDate,
//            @RequestParam("endDate") LocalDate endDate) {
//        return reportService.getOrderCountByDateRange(startDate, endDate);
//    }
//
//    @GetMapping("/order-count-for-last-7-days")
//    public List<Object[]> getOrderCountForLast7Days() {
//        LocalDate endDate = LocalDate.now();
//        LocalDate startDate = endDate.minusDays(7);
//        return reportService.getOrderCountForLast7Days(startDate, endDate);
//    }
//
//    @GetMapping("/order-count-by-month")
//    public List<Object[]> getOrderCountByMonth(
//            @RequestParam("month") int month,
//            @RequestParam("year") int year) {
//        return reportService.getOrderCountByMonth(month, year);
//    }

//    @GetMapping("/revenue-by-month")
//    public List<Object[]> getRevenueByMonth(
//            @RequestParam("month") int month,
//            @RequestParam("year") int year) {
//        return reportService.getRevenueByMonth(month, year);
//    }


//    @GetMapping("/orders")
//    public List<Order> getOrders(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
//        return reportService.getOrdersBetweenDates(startDate, endDate);
//    }

//    @GetMapping("/revenue")
//    public Double getTotalRevenue(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
//        return reportService.getTotalRevenueBetweenDates(startDate, endDate);
//    }
//
//    @GetMapping("/orders/count")
//    public Long countOrdersByDayOfWeek(@RequestParam int dayOfWeek) {
//        return reportService.countOrdersByDayOfWeek(dayOfWeek);
//    }

//    public Double getRevenue(LocalDate startDate, LocalDate endDate) {
//        return orderRepository.findTotalRevenueBetween(startDate, endDate);
//    }
//
//    public Long getOrderCount(LocalDate startDate, LocalDate endDate) {
//        return orderRepository.countOrdersBetween(startDate, endDate);
//    }
//
//    public List<Long> getWeeklyOrderCount(LocalDate startDate, LocalDate endDate) {
//        List<Long> weeklyCounts = new ArrayList<>();
//        for (int i = 1; i <= 7; i++) {
//            weeklyCounts.add(orderRepository.countOrdersByDayOfWeek(i, startDate, endDate));
//        }
//        return weeklyCounts;
//    }

    @GetMapping("/revenue")
    public Double getRevenue(@RequestParam String startDate, @RequestParam String endDate) {
        System.out.println(reportService.getRevenue(LocalDate.parse(startDate), LocalDate.parse(endDate)));
        return reportService.getRevenue(LocalDate.parse(startDate), LocalDate.parse(endDate));
    }

    @GetMapping("/orders/count")
    public Long getOrderCount(@RequestParam String startDate, @RequestParam String endDate) {
        return reportService.getOrderCount(LocalDate.parse(startDate), LocalDate.parse(endDate));
    }

    @GetMapping("/orders/weekly")
    public List<Long> getWeeklyOrderCount(@RequestParam String startDate, @RequestParam String endDate) {
        return reportService.getWeeklyOrderCount(LocalDate.parse(startDate), LocalDate.parse(endDate));
    }


    @GetMapping("/revenue-by-day")
    public List<RevenueByDayDTO> getRevenueByDay(
            @RequestParam String startDate, @RequestParam String endDate) {
        // Convert startDate and endDate to LocalDate
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        // Fetch revenue data by day
        return reportService.getRevenueByDay(start, end);
    }

    @RequestMapping(value = "/orders-by-day", method = RequestMethod.GET)
    public ResponseEntity<?> getOrdersByDay(@RequestParam String startDate, @RequestParam String endDate) {
        try {
            // Chuyển đổi chuỗi ngày từ client thành LocalDate
            LocalDate start = LocalDate.parse(startDate); // Dùng LocalDate.parse nếu định dạng ngày đúng
            LocalDate end = LocalDate.parse(endDate);

            // Xử lý dữ liệu
            List<OrdersByDayDTO> orders = reportService.getOrdersByDay(start, end);
            return ResponseEntity.ok(orders);
        } catch (DateTimeParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date format");
        }
    }

    @GetMapping("/weekly")
    public ResponseEntity<List<WeeklyReportDTO>> getWeeklyReport() {
        List<WeeklyReportDTO> weeklyReport = reportService.getWeeklyReport();
        return ResponseEntity.ok(weeklyReport);
    }
}
