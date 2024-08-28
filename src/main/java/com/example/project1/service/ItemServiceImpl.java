package com.example.project1.service;

import com.example.project1.entity.Category;
import com.example.project1.entity.Item;
import com.example.project1.repository.CategoryRepository;
import com.example.project1.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService{

    Logger LOGGER = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public List<Item> fetchItemList() {
        return itemRepository.findAll();
    }

    @Override
    public Item saveItem(Long categoryId, String categoryName, Item item) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));

        item.setCategory(category);
        return itemRepository.save(item);
    }

    @Override
    public List<Item> fetchItemByName(String name) {
        return itemRepository.findByItemNameContainingIgnoreCase(name);
    }
}
