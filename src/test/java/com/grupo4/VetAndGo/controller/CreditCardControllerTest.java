package com.grupo4.VetAndGo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo4.VetAndGo.domain.model.BankAccount;
import com.grupo4.VetAndGo.domain.model.BankTransaction;
import com.grupo4.VetAndGo.domain.model.Client;
import com.grupo4.VetAndGo.domain.model.CreditCard;
import com.grupo4.VetAndGo.domain.service.CreditCardService;
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

@WebMvcTest(CreditCardController.class)
public class CreditCardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreditCardService creditCardService;

    @Autowired
    private ObjectMapper objectMapper;

    private CreditCard creditCard;
    private BankAccount bankAccount;
    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client(1L, "user1", "pass1", "John", "Doe", "Smith", "12345678A");
        bankAccount = new BankAccount(1L, new BigDecimal("1000.00"), "ES1234567890123456789012", client);
        creditCard = new CreditCard(1L, "1234567890123456", "2026-12", "123", "John Doe Smith", bankAccount);
    }

    @Test
    void findAll_ShouldReturnListOfCreditCards() throws Exception {
        List<CreditCard> cards = Arrays.asList(creditCard);
        when(creditCardService.findAll()).thenReturn(cards);

        mockMvc.perform(get("/api/credit-cards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].cardNumber").value("1234567890123456"))
                .andExpect(jsonPath("$[0].fullName").value("John Doe Smith"));
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenNoCards() throws Exception {
        when(creditCardService.findAll()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/credit-cards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }

    @Test
    void findById_ShouldReturnCreditCard_WhenCardExists() throws Exception {
        when(creditCardService.findById(1L)).thenReturn(Optional.of(creditCard));

        mockMvc.perform(get("/api/credit-cards/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardNumber").value("1234567890123456"))
                .andExpect(jsonPath("$.fullName").value("John Doe Smith"))
                .andExpect(jsonPath("$.expirationDate").value("2026-12"));
    }

    @Test
    void findById_ShouldReturnNotFound_WhenCardDoesNotExist() throws Exception {
        when(creditCardService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/credit-cards/{id}", 99L))
                .andExpect(status().isNotFound());
    }

    @Test
    void findByClientId_ShouldReturnListOfCreditCards() throws Exception {
        List<CreditCard> cards = Arrays.asList(creditCard);
        when(creditCardService.findByClientId(1L)).thenReturn(cards);

        mockMvc.perform(get("/api/credit-cards/client/{clientId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].cardNumber").value("1234567890123456"));
    }

    @Test
    void findByClientId_ShouldReturnEmptyList_WhenClientHasNoCards() throws Exception {
        when(creditCardService.findByClientId(99L)).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/credit-cards/client/{clientId}", 99L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }

    @Test
    void findByClientId_ShouldReturnMultipleCards() throws Exception {
        CreditCard card2 = new CreditCard(2L, "9876543210987654", "2025-06", "456", "Jane Smith", bankAccount);
        List<CreditCard> cards = Arrays.asList(creditCard, card2);
        when(creditCardService.findByClientId(1L)).thenReturn(cards);

        mockMvc.perform(get("/api/credit-cards/client/{clientId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].cardNumber").value("1234567890123456"))
                .andExpect(jsonPath("$[1].cardNumber").value("9876543210987654"));
    }

    @Test
    void getTransactions_ShouldReturnListOfTransactions() throws Exception {
        List<BankTransaction> transactions = Arrays.asList();
        when(creditCardService.findTransactionsByCardId(1L)).thenReturn(transactions);

        mockMvc.perform(get("/api/credit-cards/transactions/{cardId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }

    @Test
    void getMonthlySpending_ShouldReturnSpendingAmount() throws Exception {
        BigDecimal spending = new BigDecimal("500.00");
        when(creditCardService.calculateMonthlySpending(1L)).thenReturn(spending);

        mockMvc.perform(get("/api/credit-cards/spending/{cardId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(500.00));
    }

    @Test
    void getMonthlySpending_ShouldReturnZero_WhenNoSpending() throws Exception {
        BigDecimal spending = BigDecimal.ZERO;
        when(creditCardService.calculateMonthlySpending(1L)).thenReturn(spending);

        mockMvc.perform(get("/api/credit-cards/spending/{cardId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(0));
    }

    @Test
    void findByBankAccountId_ShouldReturnListOfCreditCards() throws Exception {
        List<CreditCard> cards = Arrays.asList(creditCard);
        when(creditCardService.findByBankAccountId(1L)).thenReturn(cards);

        mockMvc.perform(get("/api/credit-cards/account/{bankAccountId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].cardNumber").value("1234567890123456"));
    }
}
