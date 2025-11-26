package com.demo.userService.ControllerAdvices;

import com.demo.userService.exceptions.InvalidTokenException;
import com.demo.userService.exceptions.PasswordMismatchException;
import com.demo.userService.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalRExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException userNotFoundException){
        Map<String, Object> map = Map.of(
                "error", "UserNotFoundException",
                "message", userNotFoundException.getMessage(),
                "status", 404
        );
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<Map<String, Object>> handlePasswordMismatchException(PasswordMismatchException passwordMismatchException){
        Map<String , Object> map = Map.of(
                "error", "PasswordMismatchException",
                "message", passwordMismatchException.getMessage(),
                "status",400
        );
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidTokenException(InvalidTokenException invalidTokenException){
        Map<String , Object> map = Map.of(
                "error", "InvalidTokenException",
                "message", invalidTokenException.getMessage(),
                "status",401
        );
        return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
    }
}
