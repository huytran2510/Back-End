package com.example.backendproject.repository;

import com.example.backendproject.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository  extends JpaRepository<Order,Long> {

    Optional<Order> findByOrderId(String id);
}
