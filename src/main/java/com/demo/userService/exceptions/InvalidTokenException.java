package com.demo.userService.exceptions;

public class InvalidTokenException extends Exception {
    public InvalidTokenException(String message){
        super(message);
    }
}
