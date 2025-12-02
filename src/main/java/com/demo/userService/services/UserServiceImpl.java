package com.demo.userService.services;

import com.demo.userService.exceptions.InvalidTokenException;
import com.demo.userService.exceptions.PasswordMismatchException;
import com.demo.userService.exceptions.UserNotFoundException;
import com.demo.userService.models.Token;
import com.demo.userService.models.User;
import com.demo.userService.repositories.TokenRepository;
import com.demo.userService.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenRepository tokenRepository;
    private SecretKey secretKey;

    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           TokenRepository tokenRepository,
                           SecretKey secretKey) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
        this.secretKey = secretKey;
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
    public String login(String email, String password) throws UserNotFoundException, PasswordMismatchException {
        Optional<User> optional = userRepository.findByEmail(email);
        if(optional.isEmpty()){
            throw new UserNotFoundException("User with email "+email+" not found");
        }

        User userExists = optional.get();
        //when login successful
//        if(bCryptPasswordEncoder.matches(password, userExists.getPassword())){
//            Token token = new Token();
//            token.setUser(userExists);
//            //random alphanumeric string of length 120 which is given by Apache commons lang3
//            token.setTokenValue(RandomStringUtils.randomAlphanumeric(120));
//            token.setExpiryDate(LocalDateTime.now().plusDays(2));// 1 day
//            return tokenRepository.save(token);
//        }
//        else{
//            throw new PasswordMismatchException("Invalid credentials");
//        }
        //generate JWT token using JJWT library
       // let's not hardcode the payload, instead create a payload using  user Details
//        String payload = """
//        {
//             "email":"john.doe@example.com",
//             "userId":12345,
//             "roles": ["Student", "USER"],
//             "expiryDate":"2025-11-28T23:59:59"
//        }""";

        Map<String, Object> claims = new HashMap<>();
        claims.put("iss", "DemoApp");
        claims.put("sub", "UserAuthentication");
        claims.put("email", userExists.getEmail());
        claims.put("userId", userExists.getId());
        claims.put("roles", new String[]{"USER"});

        Date expirationDate = Date.from(LocalDateTime.now().plusDays(2)
                .atZone(ZoneId.systemDefault())
                .toInstant());
        claims.put("exp", expirationDate);

        //claims.put("exp", LocalDateTime.now().plusDays(2).toString());

        //Generate Secret Key
        //But its creates new key every time, so in real world app we should use a fixed secret key what we can store in env variable or config file
//        MacAlgorithm macAlgorithm = Jwts.SIG.HS256;
//        SecretKey secretKey = macAlgorithm.key().build();

    //    byte[] payloadBytes = payload.getBytes();
        //   String token = Jwts.builder().content(payloadBytes).compact();
        String jwtToken = Jwts.builder().claims(claims).signWith(secretKey).compact();

        return jwtToken;
    }

    @Override
    public User validateToken(String tokenValue) throws InvalidTokenException {
//        Optional<Token> optionalToken = tokenRepository.findByTokenValueAndExpiryDateGreaterThan(tokenValue, LocalDateTime.now());
//        if(optionalToken.isEmpty()){
//            throw new InvalidTokenException("Token is invalid or expired");
//        }
//        //If Token is Valid, return the associated User
//        return optionalToken.get().getUser();

        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
        Claims payload = jwtParser.parseSignedClaims(tokenValue).getPayload();
        //Check token expiry
        Date expiryDate = payload.get("exp", Date.class);
        if(expiryDate.before(new Date())) {
            throw new InvalidTokenException("Token is expired");

        }
        String email = payload.get("email", String.class);
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            throw new InvalidTokenException("Token is invalid - User not found");
        }
        return optionalUser.get();
    }

    @Override
    public String logout(String tokenValue) {
        return "";
    }
}
