package com.example.springapp.controller;

import com.example.springapp.model.Expense;
import com.example.springapp.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081")
public class ExpenseController {
    
    @Autowired
    private ExpenseRepository expenseRepository;
    
    @PostMapping("/expenses")
    public ResponseEntity<?> createExpense(@RequestBody Expense expense) {
        if (expense.getAmount() == null || expense.getAmount() <= 0) {
            return ResponseEntity.badRequest().body("Amount must be greater than 0");
        }
        if (expense.getDescription() == null || expense.getDescription().length() < 5 || expense.getDescription().length() > 200) {
            return ResponseEntity.badRequest().body("Description must be between 5-200 characters");
        }
        if (expense.getDate() == null || expense.getDate().isAfter(LocalDate.now())) {
            return ResponseEntity.badRequest().body("Date cannot be in the future");
        }
        if (expense.getEmployeeId() == null) {
            return ResponseEntity.badRequest().body("Employee ID is required");
        }
        
        return ResponseEntity.ok(expenseRepository.save(expense));
    }
    
    @GetMapping("/expenses")
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }
    
    @PutMapping("/expenses/{id}/status")
    public ResponseEntity<?> updateExpenseStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        Optional<Expense> expenseOpt = expenseRepository.findById(id);
        if (!expenseOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        Expense expense = expenseOpt.get();
        String status = request.get("status");
        String remarks = request.get("remarks");
        
        if ("REJECTED".equals(status) && (remarks == null || remarks.trim().isEmpty())) {
            return ResponseEntity.badRequest().body("Remarks required for rejection");
        }
        
        expense.setStatus(status);
        expense.setRemarks(remarks);
        
        return ResponseEntity.ok(expenseRepository.save(expense));
    }
}