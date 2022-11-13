
package com.signup.service.signupService.model;


import java.util.Map;

public class PayloadValidation {

    public static boolean createdPayloadIdValidation(User user){


        if(user.getId()!=null){
            return false;
        }

        return true;
    }

    public static boolean createdPayloadUsernameField(Map<String, Object> map){

        if(!map.containsKey("username")) return false;
        return true;
    }


}
