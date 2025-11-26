package com.demo.userService.services;

import com.demo.userService.exceptions.InvalidTokenException;
import com.demo.userService.exceptions.PasswordMismatchException;
import com.demo.userService.exceptions.UserNotFoundException;
import com.demo.userService.models.Token;
import com.demo.userService.models.User;
import com.demo.userService.repositories.TokenRepository;
import com.demo.userService.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenRepository tokenRepository;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, TokenRepository tokenRepository){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public User signUp(String name, String email, String password) {
        //check if user already exists
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            return optionalUser.get();
        }
        User user = new User();
        user.setUsername(name);
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
        return user;
    }

    @Override
    public Token login(String email, String password) throws UserNotFoundException, PasswordMismatchException {
        Optional<User> optional = userRepository.findByEmail(email);
        if(optional.isEmpty()){
            throw new UserNotFoundException("User with email "+email+" not found");
        }

        User userExists = optional.get();
        //when login successful
        if(bCryptPasswordEncoder.matches(password, userExists.getPassword())){
            Token token = new Token();
            token.setUser(userExists);
            //random alphanumeric string of length 120 which is given by Apache commons lang3
            token.setTokenValue(RandomStringUtils.randomAlphanumeric(120));
            token.setExpiryDate(LocalDateTime.now().plusDays(2));// 1 day
            return tokenRepository.save(token);
        }
        else{
            throw new PasswordMismatchException("Invalid credentials");
        }
    }

    @Override
    public User validateToken(String tokenValue) throws InvalidTokenException {
        Optional<Token> optionalToken = tokenRepository.findByTokenValueAndExpiryDateGreaterThan(tokenValue, LocalDateTime.now());
        if(optionalToken.isEmpty()){
            throw new InvalidTokenException("Token is invalid or expired");
        }
        //If Token is Valid, return the associated User
        return optionalToken.get().getUser();
    }

    @Override
    public String logout(String tokenValue) {
        return "";
    }
}
