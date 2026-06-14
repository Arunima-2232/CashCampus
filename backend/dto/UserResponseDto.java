package com.expanse.expanse.manager.dto;

import lombok.Data;

@Data
public class UserResponseDto {
    private String emailId;
    private String name;
    private String mobileNumber;
    private String role;
    private String status;
    private String token;
}
