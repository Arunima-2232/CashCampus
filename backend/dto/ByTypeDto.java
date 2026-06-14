package com.expanse.expanse.manager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ByTypeDto {
    @Pattern(regexp = "^Income|Expense$", message = "Only alphabets are allowed")
    private String type;

    @Email(message = "must provide email")
    private String emailId;
}
