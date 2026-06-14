package com.expanse.expanse.manager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="User_Table")
@Data
public class User {
    @Id
    private String emailId;
    private String password;
    private String name;
    private String mobileNumber;
    private String role;
    private String status;
}
