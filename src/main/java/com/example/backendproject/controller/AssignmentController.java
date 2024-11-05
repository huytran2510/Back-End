package com.example.backendproject.controller;

import com.example.backendproject.domain.model.Assignment;
import com.example.backendproject.domain.model.User;
import com.example.backendproject.service.AssignmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentServiceImpl assignmentService;

    @PostMapping("")
    public ResponseEntity<?> createAssignment (@AuthenticationPrincipal User user) {
        Assignment assignment = assignmentService.save(user);
        return ResponseEntity.ok(assignment);
    }

    @GetMapping("")
    public ResponseEntity<?> getAssignments(@AuthenticationPrincipal User user) {
        Set<Assignment> assignmentSet = assignmentService.findByUser(user);
        return ResponseEntity.ok(assignmentSet);
    }

    @GetMapping("/{assignmentId}")
    public ResponseEntity<?> getAssignments(@AuthenticationPrincipal User user, @PathVariable Long assignmentId) {
        Optional<Assignment> assignmentOptional = assignmentService.findById(assignmentId);
        return ResponseEntity.ok(assignmentOptional.orElse(new Assignment()));
    }

    @PutMapping("/{assignmentId}")
    public ResponseEntity<?> updateAssignment(
            @PathVariable Long assignmentId,
            @RequestBody Assignment assignment,
            @AuthenticationPrincipal User user) {
        Assignment updateAssignment = assignmentService.create(assignment);
        return ResponseEntity.ok(updateAssignment);
    }
}
