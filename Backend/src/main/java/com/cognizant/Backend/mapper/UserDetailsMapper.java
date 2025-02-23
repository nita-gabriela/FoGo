package com.cognizant.Backend.mapper;

import com.cognizant.Backend.dto.RegisterDto;
import com.cognizant.Backend.entity.UserDetails;

public class UserDetailsMapper {
    public static UserDetails toUserDetails(RegisterDto registerDto) {
        return UserDetails.builder()
                .name(registerDto.getName())
                .phoneNumber(registerDto.getPhoneNumber())
                .build();
    }
}
