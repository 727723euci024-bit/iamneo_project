package com.example.springapp.controller;

import com.example.springapp.model.Expense;
import com.example.springapp.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ExpenseController {
    
    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/expenses")
    public ResponseEntity<?> createExpense(@RequestBody Expense expense) {
        // Validation
        if (expense.getAmount() == null || expense.getAmount() <= 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "Validation failed", "message", "Amount must be greater than 0"));
        }
        
        if (expense.getDescription() == null || expense.getDescription().trim().isEmpty() || 
            expense.getDescription().length() < 5 || expense.getDescription().length() > 200) {
            return ResponseEntity.badRequest().body(Map.of("error", "Validation failed", "message", "Description must be between 5-200 characters"));
        }
        
        if (expense.getDate() == null || expense.getDate().isAfter(LocalDate.now())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Validation failed", "message", "Date cannot be in the future"));
        }
        
        if (expense.getEmployeeId() == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Validation failed", "message", "Employee ID is required"));
        }

        expense.setStatus("PENDING");
        Expense savedExpense = expenseService.saveExpense(expense);
        return ResponseEntity.status(201).body(savedExpense);
    }

    @GetMapping("/expenses")
    public ResponseEntity<List<Expense>> getAllExpenses() {
        List<Expense> expenses = expenseService.getAllExpenses();
        return ResponseEntity.ok(expenses);
    }

    @PutMapping("/expenses/{id}/status")
    public ResponseEntity<?> updateExpenseStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        Optional<Expense> expenseOpt = expenseService.getExpenseById(id);
        
        if (!expenseOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Expense expense = expenseOpt.get();
        String status = request.get("status");
        String remarks = request.get("remarks");

        // Validation
        if (!"APPROVED".equals(status) && !"REJECTED".equals(status)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Validation failed", "message", "Status must be either APPROVED or REJECTED"));
        }

        if ("REJECTED".equals(status) && (remarks == null || remarks.trim().isEmpty())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Validation failed", "message", "Remarks are required when rejecting an expense"));
        }

        expense.setStatus(status);
        expense.setRemarks(remarks);
        
        Expense updatedExpense = expenseService.updateExpense(expense);
        return ResponseEntity.ok(updatedExpense);
    }
}