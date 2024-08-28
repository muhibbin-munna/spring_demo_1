package com.example.project1.service;

import com.example.project1.entity.Item;

import java.util.List;

public interface ItemService {

    List<Item> fetchItemList();

    Item saveItem(Long categoryId, String categoryName, Item item);

    List<Item> fetchItemByName(String name);
}
