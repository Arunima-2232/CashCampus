package com.expanse.expanse.manager.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ExpenseOutputDto {
    @NotBlank(message = "must provide title")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Only alphabets are allowed")
    @Size(min = 3, max = 50, message = "Title should be in range 3 to 50 char")
    private String title;

    @NotNull(message = "must provide amount")
    @Positive(message = "Amount Should be positive")
    private Double amount;

    @NotBlank(message = "must provide category")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Only alphabets are allowed")
    @Size(min = 3, max = 50,message = "category length should be in range 3 to 50 char")
    private String category;

    @Pattern(regexp = "^[A-Za-z ]+$", message = "Only alphabets are allowed")
    @NotBlank(message = "must provide description")
//    @Size(min = 3, max = 50,message = "description length should be in range 3 to 50 char")
    private String description;

    private long id;
}
