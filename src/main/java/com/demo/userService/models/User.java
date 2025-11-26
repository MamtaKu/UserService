package com.demo.userService.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseModel {
    private String username;
    private String email;
    private String password;
    @ManyToMany
    private List<Role> roles;

}

//User -> Roles
//1 User can have multiple Roles
//1 Role can belong to multiple Users
//Many to Many relationship