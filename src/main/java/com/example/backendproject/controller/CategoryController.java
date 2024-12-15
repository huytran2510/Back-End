package com.example.backendproject.controller;

import com.example.backendproject.domain.model.Category;
import com.example.backendproject.service.templates.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;





    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategory() {
        List<Category> categoryList = categoryService.getAll();
        if(!categoryList.isEmpty()){
            return ResponseEntity.ok(categoryList);
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
