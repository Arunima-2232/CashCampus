package com.expanse.expanse.manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="Expense_Manager")
@Getter @Setter
public class ExMg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String emailId;
    private String title;
    private Double amount;
    private String category;
    private LocalDate date;
    private String description;
    private String type;
}
