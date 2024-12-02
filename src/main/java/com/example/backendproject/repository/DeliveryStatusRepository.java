package com.example.backendproject.repository;

import com.example.backendproject.domain.model.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryStatusRepository extends JpaRepository<DeliveryStatus,Long> {
}
