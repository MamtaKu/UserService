package com.demo.userService.OAuth2;

import com.demo.userService.models.Role;
import org.springframework.security.core.GrantedAuthority;

public class CustomGrantedAuthority implements GrantedAuthority {
    private Role role;
    public CustomGrantedAuthority(Role role){
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.getName();
    }
}
