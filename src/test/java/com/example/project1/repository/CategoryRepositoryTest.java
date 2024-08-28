package com.example.project1.repository;

import com.example.project1.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void save(){
        Category category = Category.builder().categoryName("Electronics").build();
        categoryRepository.save(category);
    }

}