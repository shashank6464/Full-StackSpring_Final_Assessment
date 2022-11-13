package com.shop.service.shopService.controller;

import com.shop.service.shopService.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;


@FeignClient("shopManagement-service/product")
public interface ProductConsumer {


    @RequestMapping(value = "/get-products", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    List<Product> getProducts();

    @RequestMapping(value = "/get-product-by-field", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    List<Product> getProductByField(@RequestBody Map<String, Object> map);

}
