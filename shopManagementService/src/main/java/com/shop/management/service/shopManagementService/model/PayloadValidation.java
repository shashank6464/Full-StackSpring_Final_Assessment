
package com.shop.management.service.shopManagementService.model;


import java.util.Map;

public class PayloadValidation {

    public static boolean createdPayloadIdValidation(Category category){


        if(category.getCategory_id()>0){
            return false;
        }

        return true;
    }

    public static boolean createdPayloadIdValidation(Product product){

        if(product.getProduct_id()>0){
            return false;
        }

        return true;
    }


    public static boolean createdPayloadCategoryField(Map<String, Object> map){

        if(!map.containsKey("description") && !map.containsKey("name")) return false;
        return true;
    }

    public static boolean createdPayloadProductField(Map<String, Object> map){

        if(!map.containsKey("description") && !map.containsKey("price") && !map.containsKey("name")) return false;
        return true;
    }


}
