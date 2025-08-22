package com.example.springapp.model;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long employeeId;
    private Double amount;
    private String description;
    private LocalDate date;
    private String status = "PENDING";
    private String remarks;
}