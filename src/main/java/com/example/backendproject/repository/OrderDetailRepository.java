package com.example.backendproject.repository;


import com.example.backendproject.domain.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {

    @Query("SELECT od from OrderDetail od where od.order.orderId = :orderId ")
    List<OrderDetail> findAllByOrderId(@Param("orderId") String orderId);
}
