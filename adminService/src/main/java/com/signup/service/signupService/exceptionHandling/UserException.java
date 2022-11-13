package com.signup.service.signupService.exceptionHandling;




public class UserException extends Exception{

    private String errorMessage;

    public UserException(){}

    public UserException(String errorMessage){
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage(){
        return this.errorMessage;
    }

}
