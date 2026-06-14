package com.expanse.expanse.manager.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseDto {
    private LocalDateTime time;
    private String message;

}
