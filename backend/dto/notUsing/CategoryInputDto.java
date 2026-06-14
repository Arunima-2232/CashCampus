package com.expanse.expanse.manager.dto.notUsing;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryInputDto {
    @NotBlank(message = "must provide category")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Only alphabets are allowed")
    @Size(min = 3, max = 50,message = "category length should be in range 3 to 50 char")
    private String category;
}
