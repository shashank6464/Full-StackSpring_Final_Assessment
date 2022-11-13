package com.shop.service.shopService.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    private int category_id;
    private String name;
    private String description;

    @Override
    public String toString() {
        return "{" +
                "\"category_id\":" + category_id +
                ", \"name\":\"" + name + "\"" +
                ", \"description\":\"" + description + "\"" +
                '}';
    }
}
