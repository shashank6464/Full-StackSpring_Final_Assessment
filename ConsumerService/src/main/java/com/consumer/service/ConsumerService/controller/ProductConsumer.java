package com.consumer.service.ConsumerService.controller;

import com.consumer.service.ConsumerService.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@FeignClient("shopManagement-service/product")
public interface ProductConsumer {

    //add a product
    @RequestMapping(value = "/add-product", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    Product addProduct(@RequestBody Product product);

    //add multiple products
    @RequestMapping(value = "/add-products", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    List<Product> addProducts(@RequestBody List<Product> products);

    //get all products
    @RequestMapping(value = "/get-products", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    List<Product> getProducts();

    //get products by product id, category id
    @RequestMapping(value = "/product-with-category/{product_id}/{category_id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    Product assignDetails(@PathVariable("product_id") int product_id, @PathVariable("category_id") int category_id);


    //update product by id
    @RequestMapping(value = "/update-product-by-id/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    String updateProductById(@PathVariable("id") int product_id, @RequestBody Map<String, Object> map);

    //delete product by id
    @RequestMapping(value = "/delete-product-by-id/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    String deleteProductById(@PathVariable("id") int product_id);


}