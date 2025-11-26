package com.demo.userService.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
public class Token extends BaseModel {
    private String tokenValue;
    private LocalDateTime expiryDate;
    @ManyToOne
    private User user;

}

// Token -- User
// 1 Token belongs to 1 User
// 1 User can have multiple Tokens
