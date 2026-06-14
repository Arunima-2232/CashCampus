package com.expanse.expanse.manager.dto;

import com.expanse.expanse.manager.entity.ExMg;
import lombok.Data;

import java.util.List;

@Data
public class ShowExpenseDto {
    private String emailId;
    private String name;
    private String mobileNumber;
    private String role;
    private String status;

    private List<ExMg> expenses;
}
