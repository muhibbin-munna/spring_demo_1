package com.example.project1.controller;

import com.example.project1.entity.Category;
import com.example.project1.entity.Item;
import com.example.project1.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    @Autowired
    ItemService itemService;

    @GetMapping("/items")
    public List<Item> fetchItemList(){
        return itemService.fetchItemList();
    }

    @GetMapping("/item")
    public List<Item> fetchItemByName(@Validated @RequestParam String name){
        return itemService.fetchItemByName(name);
    }


//    @PostMapping("/item/add/{categoryId}")
//    public Item saveItem(@PathVariable Long categoryId, @RequestBody Item item){
//
//        return(itemService.saveItem(categoryId, item));
//    }

    @PostMapping("/item/add")
    public Item saveItem(
            @Validated @RequestParam(name = "categoryId", required = false) Long categoryId,
            @Validated @RequestParam(name = "categoryName", required = false) String categoryName,
            @RequestBody Item item) {

        return itemService.saveItem(categoryId, categoryName, item);
    }

}
