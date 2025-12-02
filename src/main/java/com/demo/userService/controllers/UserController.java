package com.demo.userService.controllers;

import com.demo.userService.dtos.LoginRequestDto;
import com.demo.userService.dtos.SignUpRequestDto;
import com.demo.userService.dtos.TokenDto;
import com.demo.userService.dtos.UserDto;
import com.demo.userService.exceptions.InvalidTokenException;
import com.demo.userService.exceptions.PasswordMismatchException;
import com.demo.userService.exceptions.UserNotFoundException;
import com.demo.userService.models.Token;
import com.demo.userService.models.User;
import com.demo.userService.services.UserService;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    public  UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        User user = userService.signUp(signUpRequestDto.getName(), signUpRequestDto.getEmail(), signUpRequestDto.getPassword());
        return UserDto.from(user);
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto) throws UserNotFoundException, PasswordMismatchException {
        String token = userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
       // TokenDto tokenDto = TokenDto.from(token);
        return token;
    }
    @GetMapping("/validateToken/{tokenValue}")
    public UserDto validateToken(@PathVariable("tokenValue") String tokenValue) throws InvalidTokenException {
        User user = userService.validateToken(tokenValue);
        return UserDto.from(user);
    }

    @PostMapping("/logout")
    public String logout(@RequestBody TokenDto tokenDto){
        // Logic to handle user logout
        return "Logged out successfully";
    }
}
