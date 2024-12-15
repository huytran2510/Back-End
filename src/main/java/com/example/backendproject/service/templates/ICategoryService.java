package com.example.backendproject.service.templates;

import com.example.backendproject.domain.model.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    List<Category> getAll();
    Optional<Category> findById(Long id);
}
