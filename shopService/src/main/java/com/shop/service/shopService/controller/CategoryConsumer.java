package com.shop.service.shopService.controller;


import com.shop.service.shopService.model.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@FeignClient("shopManagement-service/category")
public interface CategoryConsumer {

    @GetMapping("/check")
    String check();

    @RequestMapping(value = "/get-categories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    List<Category> getCategories();


    @RequestMapping(value = "/get-category-by-field", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    List<Category> getCategoriesByField(@RequestBody Map<String, Object> map);

}
