package com.example.project1.service;

import com.example.project1.entity.Category;

import java.util.List;


public interface CategoryService    {
    List<Category> fetchCategoryList();

    Category saveCategory(Category category);

    List<Category> fetchCategoryByName(String name);
}
