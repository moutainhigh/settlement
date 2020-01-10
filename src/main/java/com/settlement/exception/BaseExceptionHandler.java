package com.settlement.exception;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(value = AuthorizationException.class)
    public String error(HttpServletRequest request, HttpServletResponse response,AuthorizationException authorizationException){
        return "/auth";
    }
}
