package com.grupo4.VetAndGo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo4.VetAndGo.domain.model.BankAccount;
import com.grupo4.VetAndGo.domain.model.BankTransaction;
import com.grupo4.VetAndGo.domain.model.Client;
import com.grupo4.VetAndGo.domain.model.TransactionOrigin;
import com.grupo4.VetAndGo.domain.model.TransactionType;
import com.grupo4.VetAndGo.domain.service.BankTransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BankTransactionController.class)
public class BankTransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankTransactionService bankTransactionService;

    @Autowired
    private ObjectMapper objectMapper;

    private BankTransaction transaction;
    private BankAccount bankAccount;
    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client(1L, "user1", "pass1", "John", "Doe", "Smith", "12345678A");
        bankAccount = new BankAccount(1L, new BigDecimal("1000.00"), "ES1234567890123456789012", client);
        transaction = new BankTransaction(
                1L,
                "2026-01-15T10:30:00",
                new BigDecimal("100.00"),
                "Test transaction",
                TransactionType.DEBIT,
                TransactionOrigin.CARD,
                "1234567890123456",
                bankAccount
        );
    }

    @Test
    void findAll_ShouldReturnListOfTransactions() throws Exception {
        List<BankTransaction> transactions = Arrays.asList(transaction);
        when(bankTransactionService.findAll()).thenReturn(transactions);

        mockMvc.perform(get("/api/bank-transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].concept").value("Test transaction"))
                .andExpect(jsonPath("$[0].amount").value(100.00));
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenNoTransactions() throws Exception {
        when(bankTransactionService.findAll()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/bank-transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }

    @Test
    void findById_ShouldReturnTransaction_WhenTransactionExists() throws Exception {
        when(bankTransactionService.findById(1L)).thenReturn(Optional.of(transaction));

        mockMvc.perform(get("/api/bank-transactions/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.concept").value("Test transaction"))
                .andExpect(jsonPath("$.amount").value(100.00))
                .andExpect(jsonPath("$.type").value("DEBIT"));
    }

    @Test
    void findById_ShouldReturnNotFound_WhenTransactionDoesNotExist() throws Exception {
        when(bankTransactionService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/bank-transactions/{id}", 99L))
                .andExpect(status().isNotFound());
    }

    @Test
    void findByAccountId_ShouldReturnListOfTransactions() throws Exception {
        List<BankTransaction> transactions = Arrays.asList(transaction);
        when(bankTransactionService.findByAccountId(1L)).thenReturn(transactions);

        mockMvc.perform(get("/api/bank-transactions/account/{accountId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].concept").value("Test transaction"));
    }

    @Test
    void findByAccountId_ShouldReturnEmptyList_WhenAccountHasNoTransactions() throws Exception {
        when(bankTransactionService.findByAccountId(99L)).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/bank-transactions/account/{accountId}", 99L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }

    @Test
    void findByAccountId_ShouldReturnMultipleTransactions() throws Exception {
        BankTransaction transaction2 = new BankTransaction(
                2L,
                "2026-01-16T14:30:00",
                new BigDecimal("50.00"),
                "Another transaction",
                TransactionType.CREDIT,
                TransactionOrigin.TRANSFER,
                null,
                bankAccount
        );
        List<BankTransaction> transactions = Arrays.asList(transaction, transaction2);
        when(bankTransactionService.findByAccountId(1L)).thenReturn(transactions);

        mockMvc.perform(get("/api/bank-transactions/account/{accountId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].concept").value("Test transaction"))
                .andExpect(jsonPath("$[1].concept").value("Another transaction"));
    }
}

