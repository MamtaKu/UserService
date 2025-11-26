package com.demo.userService.exceptions;

public class UserNotFoundException  extends Exception {
    public UserNotFoundException(String message){
        super(message);
    }
}
