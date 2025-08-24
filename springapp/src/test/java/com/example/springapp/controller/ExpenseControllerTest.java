package com.example.springapp.controller;

import com.example.springapp.model.Expense;
import com.example.springapp.service.ExpenseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExpenseController.class)
public class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseService expenseService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateExpense() throws Exception {
        Expense expense = new Expense();
        expense.setId(1L);
        expense.setEmployeeId(101L);
        expense.setAmount(150.75);
        expense.setDescription("Office supplies");
        expense.setDate(LocalDate.of(2023, 10, 15));
        expense.setStatus("PENDING");

        when(expenseService.saveExpense(any(Expense.class))).thenReturn(expense);

        mockMvc.perform(post("/api/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expense)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.employeeId").value(101))
                .andExpect(jsonPath("$.amount").value(150.75))
                .andExpect(jsonPath("$.description").value("Office supplies"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    public void testGetAllExpenses() throws Exception {
        Expense expense1 = new Expense();
        expense1.setId(1L);
        expense1.setEmployeeId(101L);
        expense1.setAmount(150.75);
        expense1.setDescription("Office supplies");
        expense1.setDate(LocalDate.of(2023, 10, 15));
        expense1.setStatus("PENDING");

        Expense expense2 = new Expense();
        expense2.setId(2L);
        expense2.setEmployeeId(102L);
        expense2.setAmount(75.50);
        expense2.setDescription("Transportation");
        expense2.setDate(LocalDate.of(2023, 10, 10));
        expense2.setStatus("APPROVED");

        when(expenseService.getAllExpenses()).thenReturn(Arrays.asList(expense1, expense2));

        mockMvc.perform(get("/api/expenses"))
                .andExpect(status().isOk())
                .andExpected(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    public void testUpdateExpenseStatus() throws Exception {
        Expense expense = new Expense();
        expense.setId(1L);
        expense.setEmployeeId(101L);
        expense.setAmount(150.75);
        expense.setDescription("Office supplies");
        expense.setDate(LocalDate.of(2023, 10, 15));
        expense.setStatus("APPROVED");
        expense.setRemarks("Approved as per policy");

        when(expenseService.getExpenseById(anyLong())).thenReturn(Optional.of(expense));
        when(expenseService.updateExpense(any(Expense.class))).thenReturn(expense);

        mockMvc.perform(put("/api/expenses/1/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"status\":\"APPROVED\",\"remarks\":\"Approved as per policy\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("APPROVED"))
                .andExpect(jsonPath("$.remarks").value("Approved as per policy"));
    }
}