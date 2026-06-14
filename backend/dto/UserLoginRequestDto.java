package com.expanse.expanse.manager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLoginRequestDto {
    @Email(message = "must provide email")
    private String emailId;

    @NotBlank(message = "must provide password")
    @Size(min = 5, max = 50, message = "Password should be in range 5 to 50 char")
    private String password;
}
