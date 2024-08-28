package com.example.project1.service;

import com.example.project1.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.project1.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> fetchCategoryByName(String name) {
        return categoryRepository.findByCategoryNameContainingIgnoreCase(name);
    }

    @Override
    public List<Category> fetchCategoryList() {
        return categoryRepository.findAll();
    }

}
