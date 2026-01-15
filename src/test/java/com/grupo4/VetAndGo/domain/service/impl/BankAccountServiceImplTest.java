package com.grupo4.VetAndGo.domain.service.impl;

import com.grupo4.VetAndGo.domain.model.BankAccount;
import com.grupo4.VetAndGo.domain.model.Client;
import com.grupo4.VetAndGo.domain.repository.BankAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceImplTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    private BankAccount bankAccount;
    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client(1L, "user1", "pass1", "John", "Doe", "Smith", "12345678A");
        bankAccount = new BankAccount(1L, new BigDecimal("1000.00"), "ES1234567890123456789012", client);
    }

    @Test
    void findAll_ShouldReturnListOfBankAccounts() {
        // Arrange
        List<BankAccount> accounts = Arrays.asList(bankAccount);
        when(bankAccountRepository.findAll()).thenReturn(accounts);

        // Act
        List<BankAccount> result = bankAccountService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ES1234567890123456789012", result.get(0).getIban());
        verify(bankAccountRepository, times(1)).findAll();
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenNoAccounts() {
        // Arrange
        when(bankAccountRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<BankAccount> result = bankAccountService.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(bankAccountRepository, times(1)).findAll();
    }

    @Test
    void findById_ShouldReturnBankAccount_WhenAccountExists() {
        // Arrange
        when(bankAccountRepository.findById(1L)).thenReturn(Optional.of(bankAccount));

        // Act
        Optional<BankAccount> result = bankAccountService.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("ES1234567890123456789012", result.get().getIban());
        assertEquals(new BigDecimal("1000.00"), result.get().getBalance());
        verify(bankAccountRepository, times(1)).findById(1L);
    }

    @Test
    void findById_ShouldReturnEmpty_WhenAccountDoesNotExist() {
        // Arrange
        when(bankAccountRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<BankAccount> result = bankAccountService.findById(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(bankAccountRepository, times(1)).findById(99L);
    }

    @Test
    void findByClientId_ShouldReturnListOfBankAccounts() {
        // Arrange
        List<BankAccount> accounts = Arrays.asList(bankAccount);
        when(bankAccountRepository.findByClientId(1L)).thenReturn(accounts);

        // Act
        List<BankAccount> result = bankAccountService.findByClientId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ES1234567890123456789012", result.get(0).getIban());
        verify(bankAccountRepository, times(1)).findByClientId(1L);
    }

    @Test
    void findByClientId_ShouldReturnEmptyList_WhenClientHasNoAccounts() {
        // Arrange
        when(bankAccountRepository.findByClientId(99L)).thenReturn(Arrays.asList());

        // Act
        List<BankAccount> result = bankAccountService.findByClientId(99L);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(bankAccountRepository, times(1)).findByClientId(99L);
    }

    @Test
    void findByClientId_ShouldReturnMultipleAccounts() {
        // Arrange
        BankAccount account2 = new BankAccount(2L, new BigDecimal("2000.00"), "ES9876543210987654321098", client);
        List<BankAccount> accounts = Arrays.asList(bankAccount, account2);
        when(bankAccountRepository.findByClientId(1L)).thenReturn(accounts);

        // Act
        List<BankAccount> result = bankAccountService.findByClientId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bankAccountRepository, times(1)).findByClientId(1L);
    }
}

