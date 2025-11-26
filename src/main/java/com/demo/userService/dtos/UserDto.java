package com.demo.userService.dtos;

import com.demo.userService.models.Role;
import com.demo.userService.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String email;
    private List<Role> roles;

    public static UserDto from(User user) {
        if(user == null) {
            return null;
        }
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setRoles(user.getRoles());
        return dto;
    }
}
