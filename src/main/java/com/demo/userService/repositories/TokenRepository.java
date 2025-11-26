package com.demo.userService.repositories;

import com.demo.userService.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByTokenValue(String tokenValue);
    Token save(Token token);
    //validate the token
    //check if the token is present in the table with the given value
    //now check if expiry of token > current time
    //select * from tokens where token_value = ? and expiry_date > current_time
   Optional<Token> findByTokenValueAndExpiryDateGreaterThan(String tokenValue, java.time.LocalDateTime currentTime);

}
