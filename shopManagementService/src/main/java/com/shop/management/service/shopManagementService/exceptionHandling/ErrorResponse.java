package com.shop.management.service.shopManagementService.exceptionHandling;



public class ErrorResponse {

    private int errorCode;
    private String message;


    public int getErrorCode(){
        return this.errorCode;
    }

    public void setErrorCode(int errorCode){
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
