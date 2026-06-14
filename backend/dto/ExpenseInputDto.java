package com.expanse.expanse.manager.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ExpenseInputDto {
    @NotBlank(message = "must provide title")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "title - Only alphabets are allowed")
    @Size(min = 3, max = 50, message = "Title should be in range 3 to 50 char")
    private String title;

    @NotNull(message = "must provide amount")
    @Positive(message = "Amount should be positive")
    @DecimalMin(value = "1.0", message = "Amount must be at least 1")
    @DecimalMax(value = "100000.0", message = "Amount must not exceed 100000")
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

//    @Email(message = "must provide email")
    private String emailId;

    @Pattern(regexp = "^Income|Expense$", message = "Only alphabets are allowed")
    @NotBlank(message = "must provide type of expense (Income/Expense")
    private String type;
}
