package com.demo.userService.services;

import com.demo.userService.exceptions.InvalidTokenException;
import com.demo.userService.exceptions.PasswordMismatchException;
import com.demo.userService.exceptions.UserNotFoundException;
import com.demo.userService.models.Token;
import com.demo.userService.models.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User signUp(String name, String email, String password);
    String login(String email, String password) throws UserNotFoundException, PasswordMismatchException;
    User validateToken(String tokenValue) throws InvalidTokenException;
    String logout(String tokenValue);
}
