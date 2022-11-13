package com.signup.service.signupService.exceptionHandling;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {


    // for not found
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> exceptionMovieHandler(Exception e){

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(e.getMessage());

        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }


//    // Id must not present
//    @ExceptionHandler(IdPresentException.class)
//    public ResponseEntity<ErrorResponse> exceptionHandlerIdNotPresent(Exception e){
//
//        ErrorResponse errorResponse = new ErrorResponse();
//
//        errorResponse.setErrorCode(HttpStatus.BAD_REQUEST.value());
//        errorResponse.setMessage(e.getMessage());
//
//        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
//    }
//
//    // Password Match Exception
//    @ExceptionHandler(PasswordMatchException.class)
//    public ResponseEntity<ErrorResponse> exceptionHandlerPasswordMatch(Exception e){
//
//        ErrorResponse errorResponse = new ErrorResponse();
//
//        errorResponse.setErrorCode(HttpStatus.BAD_REQUEST.value());
//        errorResponse.setMessage(e.getMessage());
//
//        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
//    }

    // Any Bad Request Exception
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> exceptionHandlerBadRequest(Exception e){

        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setErrorCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(e.getMessage());

        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    // any other bad request
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception e){

        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setErrorCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage("THE REQUEST CANNOT BE PLACED DUE TO MALFUNCTION SYNTAX, INVALID REQUEST MESSAGE FRAMING, OR DECEPTIVE REQUEST ROUTING");

        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
