package com.consumer.service.ConsumerService.controller;


import com.consumer.service.ConsumerService.model.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@FeignClient("shopManagement-service/category")
public interface CategoryConsumer {

    @GetMapping("/check")
    String check();


    //get all categories
    @RequestMapping(value = "/get-categories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    List<Category> getCategories();

    //add a category
    @RequestMapping(value = "/add-category", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    Category addCategory(@RequestBody Category category);


    //update category by id
    @RequestMapping(value = "/update-category-by-id/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    String updateCategoryById(@PathVariable("id") int category_id, @RequestBody Map<String, Object> map);


    //delete category by id
    @RequestMapping(value = "/delete-category-by-id/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    String deleteCategoryById(@PathVariable("id") int category_id);

}