package com.signup.service.signupService.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.signup.service.signupService.exceptionHandling.BadRequestException;
import com.signup.service.signupService.model.User;
import com.signup.service.signupService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    // To convert Movie object to JSON content (adding "" and {, } )
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            System.out.println(jsonContent);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public String signupService(User user) {

        if(!user.getPassword().equals(user.getPassword_confirm())){
            return "{\"message\" : \"Error!! Both passwords SHOULD MATCH !!\"}";
        }


        User savedUser = userRepository.save(user);
        return "{" +
                "\"message\":"+"\"Successfully Created User\",\n"+
                "\"data\":"+asJsonString(savedUser)+"\n"+
                "}";
    }

    public List<User> getUserByUserName(String username){
        return userRepository.loginUser(username);
    }

    public String loginService(String username, String password){

        List<User> foundUsers =  getUserByUserName(username);

//        if(foundUsers.isEmpty()){
//            return "{\n" +
//                    "\"message\":"+"\" Authentication Failed !!! (USER NOT FOUND) \",\n"+
//                    "}";
//        }

//        else
        if(!foundUsers.get(0).getPassword().equals(password)){
            return "{\n" +
                    "\"message\":"+"\" Password Incorrect !!!\"\n"+
                    "}";
        }

        return "{\n" +
                "\"message\":"+"\" Successfully Logged-in\",\n"+
                "\"data\": {\n"+"       Username : "+foundUsers.get(0).getUsername()+",\n"+
                                "       id : "+foundUsers.get(0).getId().toString()+",\n"+
                                "       Email : "+foundUsers.get(0).getEmail()+"\n"+
                            "   }\n"+
                "}";

    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

}
