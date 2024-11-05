package com.example.backendproject.service;

import com.example.backendproject.domain.model.Assignment;
import com.example.backendproject.domain.model.User;
import com.example.backendproject.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AssignmentServiceImpl {
    @Autowired
    private AssignmentRepository assignmentRepository;
    public Assignment save(User user) {
        Assignment assignment = new Assignment();
        assignment.setStatus("test");
        assignment.setUser(user);
        return assignmentRepository.save(assignment);
    }

    public Set<Assignment> findByUser(User user) {
        return assignmentRepository.findByUser(user);
    }

    public Assignment create(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }


    public Optional<Assignment> findById(Long assignmentId) {
        return assignmentRepository.findById(assignmentId);
    }
}
