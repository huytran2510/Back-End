package com.example.backendproject.repository;

import com.example.backendproject.domain.dto.forlist.*;
import com.example.backendproject.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository  extends JpaRepository<Order,Long> {

    @Query("SELECT new com.example.backendproject.domain.dto.forlist.WeeklyReportDTO(" +
            "o.orderDate, SUM(o.totalPayment), COUNT(o)) " +
            "FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate " +
            "GROUP BY o.orderDate ORDER BY o.orderDate")
    List<WeeklyReportDTO> findWeeklyReport(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    Optional<Order> findByOrderId(String id);

    @Query("SELECT new com.example.backendproject.domain.dto.forlist.RevenueByDayDTO(o.orderDate, SUM(o.totalPayment)) " +
            "FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate GROUP BY o.orderDate")
    List<RevenueByDayDTO> findRevenueByDay(@Param("startDate") LocalDate startDate,
                                           @Param("endDate") LocalDate endDate);

    @Query("SELECT new com.example.backendproject.domain.dto.forlist.OrdersByDayDTO(o.orderDate, COUNT(o.orderId)) " +
            "FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate GROUP BY o.orderDate")
    List<OrdersByDayDTO> findOrdersByDay(@Param("startDate") LocalDate startDate,
                                         @Param("endDate") LocalDate endDate);




    @Query("SELECT SUM(o.totalPayment) FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate")
    Double findTotalRevenueBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate")
    Long countOrdersBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(o) FROM Order o WHERE FUNCTION('DAYOFWEEK', o.orderDate) = :dayOfWeek AND o.orderDate BETWEEN :startDate AND :endDate")
    Long countOrdersByDayOfWeek(@Param("dayOfWeek") int dayOfWeek, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}


