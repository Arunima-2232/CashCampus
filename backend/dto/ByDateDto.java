package com.expanse.expanse.manager.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class ByDateDto {
    private LocalDate date;

    @Email(message = "must provide email")
    private String emailId;
}
