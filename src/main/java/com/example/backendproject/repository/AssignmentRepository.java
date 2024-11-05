package com.example.backendproject.repository;

import com.example.backendproject.domain.model.Assignment;
import com.example.backendproject.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    Set<Assignment> findByUser(User user);
}
