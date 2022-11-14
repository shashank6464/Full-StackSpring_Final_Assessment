package com.consumer.service.ConsumerService.controller;


import org.bson.types.ObjectId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient("token-service/auth")
public interface TokenConsumer {

    //get token from id
    @GetMapping("/get-token/{id}")
    String createToken(@PathVariable("id") ObjectId id);

    //get id from token (decoding token)
    @GetMapping("/get-userId-from-token/{token}")
    String getUserIdFromToken(@PathVariable("token") String token);

}