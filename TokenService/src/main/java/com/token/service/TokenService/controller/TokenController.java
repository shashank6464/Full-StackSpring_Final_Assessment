package com.token.service.TokenService.controller;

import com.token.service.TokenService.service.TokenService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome";
    }


    @GetMapping("/get-token/{id}")
    public String createToken(@PathVariable("id") ObjectId userId){
        return tokenService.createToken(userId);
    }

}
