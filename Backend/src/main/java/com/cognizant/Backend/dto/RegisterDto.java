package com.cognizant.Backend.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
}
