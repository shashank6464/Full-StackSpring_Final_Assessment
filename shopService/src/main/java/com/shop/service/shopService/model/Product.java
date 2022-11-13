package com.shop.service.shopService.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private int product_id;

    private String name;
    private double price;
    private String description;
    private Category category;

    @Override
    public String toString() {
        return "{" +
                "\"product_id\":" + product_id +
                ", \"name\":\"" + name + "\"" +
                ", \"price\":" + price +
                ", \"description\":\"" + description + "\"" +
                ", \"category\":" + category +
                '}';
    }
}
