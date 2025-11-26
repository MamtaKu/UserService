package com.demo.userService.exceptions;

public class PasswordMismatchException  extends Exception {
    public PasswordMismatchException(String message){
        super(message);
    }
}
