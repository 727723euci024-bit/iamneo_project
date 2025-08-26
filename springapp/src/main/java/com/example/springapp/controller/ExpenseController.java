package com.example.springapp.controller;

import com.example.springapp.model.Expense;
import com.example.springapp.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/expenses")
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
        if (expense.getAmount() <= 0) {
            return ResponseEntity.badRequest().build();
        }
        if (expense.getDescription() == null || expense.getDescription().length() < 5 || expense.getDescription().length() > 200) {
            return ResponseEntity.badRequest().build();
        }
        Expense savedExpense = expenseService.saveExpense(expense);
        return ResponseEntity.ok(savedExpense);
    }

    @GetMapping("/expenses")
    public ResponseEntity<List<Expense>> getAllExpenses() {
        List<Expense> expenses = expenseService.getAllExpenses();
        return ResponseEntity.ok(expenses);
    }

    @PutMapping("/expenses/{id}/status")
    public ResponseEntity<Expense> updateExpenseStatus(@PathVariable Long id, @RequestBody String status) {
        Optional<Expense> expenseOpt = expenseService.getExpenseById(id);
        if (expenseOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Expense expense = expenseOpt.get();
        expense.setStatus(status.replace("\"", ""));
        Expense updatedExpense = expenseService.saveExpense(expense);
        return ResponseEntity.ok(updatedExpense);
    }
}