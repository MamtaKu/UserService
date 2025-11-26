package com.demo.userService.dtos;

import com.demo.userService.models.Token;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TokenDto {
    private String tokenValue;
    private LocalDateTime expiryDate;
    private String email;


    public static TokenDto from(Token token){
        if(token == null){
            return null;
        }
        TokenDto tokenDto = new TokenDto();
        tokenDto.setTokenValue(token.getTokenValue());
        tokenDto.setExpiryDate(token.getExpiryDate());
        tokenDto.setEmail(token.getUser().getEmail());
        return tokenDto;
    }
}
