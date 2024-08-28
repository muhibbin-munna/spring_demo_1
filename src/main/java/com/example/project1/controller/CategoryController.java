package com.example.project1.controller;

import com.example.project1.entity.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.project1.service.CategoryService;

import java.util.List;


@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    private final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @PostMapping("/category/add")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Category saveCategory(@Validated @RequestBody Category category) {
        LOGGER.info("Inside saveCategory of CategoryController");
        return categoryService.saveCategory(category);
    }

    @GetMapping("/categories")
    public List<Category> fetchCategoryList(){
        LOGGER.info("Inside fetchCategoryList of CategoryController");
        return categoryService.fetchCategoryList();
    }
    @GetMapping("/category")
    public List<Category> fetchCategoryByName(@Validated @RequestParam String name){
        return categoryService.fetchCategoryByName(name);
    }
}
