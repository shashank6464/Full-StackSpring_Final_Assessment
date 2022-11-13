package com.consumer.service.ConsumerService.controller;


import com.consumer.service.ConsumerService.model.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    AdminConsumer adminConsumer;

    @Autowired
    TokenConsumer tokenConsumer;

    @GetMapping("/get-users")
    List<User> getUsers(){
        System.out.println(adminConsumer.getClass().getSimpleName());
        System.out.println("accessing from admin-service");
        return adminConsumer.getUsers();
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String signup(@RequestBody User user) throws Exception{
        try{
            return adminConsumer.signup(user);
        }
        catch (Exception e){
            String errorResponse = e.getMessage();
            int index = errorResponse.indexOf("{\"errorCode");
            System.out.println(e.getMessage());
            return errorResponse.substring(index, errorResponse.length()-1);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String login(@RequestBody Map<String, Object> map){

        try {

            String initial_response = adminConsumer.login(map);

            // if username not found || password is incorrect
            if (!initial_response.contains("data")) return initial_response;

            // else insert token with the initial response
            int id_index = initial_response.indexOf("id") + 5;
            String id = initial_response.substring(id_index,
                    initial_response.indexOf(",", id_index));


            String token = createToken(new ObjectId(id));

            //System.out.println(new ObjectId(id));

            StringBuilder response = new StringBuilder(initial_response);
            int token_index = initial_response.indexOf('}', id_index) - 4;
            response.insert(token_index, ",\n" + "       token : " + token);

            return response.toString();
        }
        catch (Exception e){
            String errorResponse = e.getMessage();
            int index = errorResponse.indexOf("{\"errorCode");
            System.out.println(e.getMessage());
            return errorResponse.substring(index, errorResponse.length()-1);
        }

    }
    @GetMapping("/get-token/{id}")
    String createToken(@PathVariable("id") ObjectId id){
        return tokenConsumer.createToken(id);
    }

}
