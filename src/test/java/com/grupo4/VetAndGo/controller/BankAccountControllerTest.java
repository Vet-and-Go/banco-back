package com.grupo4.VetAndGo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo4.VetAndGo.controller.dto.Payment;
import com.grupo4.VetAndGo.controller.dto.Origin;
import com.grupo4.VetAndGo.controller.dto.Destination;
import com.grupo4.VetAndGo.controller.dto.PaymentDetails;
import com.grupo4.VetAndGo.controller.dto.Authorization;
import com.grupo4.VetAndGo.domain.model.BankAccount;
import com.grupo4.VetAndGo.domain.model.Client;
import com.grupo4.VetAndGo.domain.service.BankAccountService;
import com.grupo4.VetAndGo.domain.service.CardPaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BankAccountController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BankAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankAccountService bankAccountService;

    @MockBean
    private CardPaymentService cardPaymentService;

    @Autowired
    private ObjectMapper objectMapper;

    private BankAccount bankAccount;
    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client(1L, "user1", "pass1", "John", "Doe", "Smith", "12345678A");
        bankAccount = new BankAccount(1L, new BigDecimal("1000.00"), "ES1234567890123456789012", client);
    }

    @Test
    void findAll_ShouldReturnListOfAccounts() throws Exception {
        List<BankAccount> accounts = Arrays.asList(bankAccount);
        when(bankAccountService.findAll()).thenReturn(accounts);

        mockMvc.perform(get("/api/bank-accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].iban").value("ES1234567890123456789012"));
    }

    @Test
    void findById_ShouldReturnAccount_WhenAccountExists() throws Exception {
        when(bankAccountService.findById(1L)).thenReturn(Optional.of(bankAccount));

        mockMvc.perform(get("/api/bank-accounts/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.iban").value("ES1234567890123456789012"));
    }

    @Test
    void findById_ShouldReturnNotFound_WhenAccountDoesNotExist() throws Exception {
        when(bankAccountService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/bank-accounts/{id}", 99L))
                .andExpect(status().isNotFound());
    }

    @Test
    void findByClientId_ShouldReturnListOfAccounts() throws Exception {
        List<BankAccount> accounts = Arrays.asList(bankAccount);
        when(bankAccountService.findByClientId(1L)).thenReturn(accounts);

        mockMvc.perform(get("/api/bank-accounts/client/{clientId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].iban").value("ES1234567890123456789012"));
    }

    @Test
    void payWithCard_ShouldReturnOk_WhenPaymentIsProcessed() throws Exception {
        Authorization auth = new Authorization("user1", "token123");
        Origin origin = new Origin("1234567890123456", "2026-12", "123", "John Doe Smith");
        Destination destination = new Destination("ES9876543210987654321098");
        PaymentDetails details = new PaymentDetails(new BigDecimal("100.00"), "Test payment");
        Payment payment = new Payment(auth, origin, destination, details);

        doNothing().when(cardPaymentService).processPayment(any());

        mockMvc.perform(post("/api/bank-accounts/payment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payment)))
                .andExpect(status().isOk());
    }
}
