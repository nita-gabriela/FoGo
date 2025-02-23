package com.cognizant.Backend.mapper;

import com.cognizant.Backend.dto.RegisterDto;
import com.cognizant.Backend.entity.Role;
import com.cognizant.Backend.entity.User;

public class UserMapper {
    public static User toUser(RegisterDto registerDto) {
        return User.builder()
                .email(registerDto.getEmail())
                .password(registerDto.getPassword())
                .role(Role.USER)
                .build();
    }
}
