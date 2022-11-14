package com.consumer.service.ConsumerService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private int id;

    private String name;
    private double price;
    private String description;
    private List<Category> categories;


    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"name\":\"" + name + "\"" +
                ", \"price\":" + price +
                ", \"description\":\"" + description + "\"" +
                ", \"categories\":" + categories +
                '}';
    }

}