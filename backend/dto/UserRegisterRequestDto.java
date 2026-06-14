package com.expanse.expanse.manager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterRequestDto {
    @Email(message = "must provide email")
    private String emailId;

    @NotBlank(message = "must provide password")
    @Size(min = 5, max = 50, message = "Password should be in range 5 to 50 char")
    private String password;

    @NotBlank(message = "must provide user name")
    @Size(max=20, min=5, message="User name has to be in range 5 to 20")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Only alphabets are allowed")
    private String name;

    @NotBlank(message = "must provide mobile number")
    @Size(max=10, min=10, message="Mobile number has to be 10 digits")
    @Pattern(regexp = "^[0-9]+$", message = "Only digits are allowed")
    private String mobileNumber;

    @NotBlank(message = "must provide role")
    private String role;
}
