package com.consumer.service.ConsumerService.configuration;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@Aspect
public class ExceptionHandlerPointcut {

    @AfterThrowing(pointcut = "execution(* some.package.service.*.*(..))", throwing = "ex")
    public void handleException(Exception ex) {
        // your common exception management code
    }

}
