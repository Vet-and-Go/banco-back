package com.grupo4.VetAndGo.domain.service.impl;

import com.grupo4.VetAndGo.domain.model.BankAccount;
import com.grupo4.VetAndGo.domain.model.BankTransaction;
import com.grupo4.VetAndGo.domain.model.TransactionOrigin;
import com.grupo4.VetAndGo.domain.model.TransactionType;
import com.grupo4.VetAndGo.domain.repository.BankTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankTransactionServiceImplTest {

    @Mock
    private BankTransactionRepository bankTransactionRepository;

    @InjectMocks
    private BankTransactionServiceImpl bankTransactionService;

    private BankTransaction transaction;
    private BankAccount bankAccount;

    @BeforeEach
    void setUp() {
        bankAccount = new BankAccount();
        bankAccount.setId(1L);
        bankAccount.setIban("ES1234567890123456789012");
        bankAccount.setBalance(new BigDecimal("1000.00"));

        transaction = new BankTransaction();
        transaction.setId(1L);
        transaction.setDate("2026-01-15T10:30:00");
        transaction.setAmount(new BigDecimal("100.00"));
        transaction.setConcept("Test transaction");
        transaction.setType(TransactionType.DEBIT);
        transaction.setOrigin(TransactionOrigin.CARD);
        transaction.setCardNumber("1234567890123456");
        transaction.setBankAccount(bankAccount);
    }

    @Test
    void findAll_ShouldReturnListOfTransactions() {
        // Arrange
        List<BankTransaction> transactions = Arrays.asList(transaction);
        when(bankTransactionRepository.findAll()).thenReturn(transactions);

        // Act
        List<BankTransaction> result = bankTransactionService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test transaction", result.get(0).getConcept());
        verify(bankTransactionRepository, times(1)).findAll();
    }

    @Test
    void findById_ShouldReturnTransaction_WhenTransactionExists() {
        // Arrange
        when(bankTransactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

        // Act
        Optional<BankTransaction> result = bankTransactionService.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Test transaction", result.get().getConcept());
        verify(bankTransactionRepository, times(1)).findById(1L);
    }

    @Test
    void findById_ShouldReturnEmpty_WhenTransactionDoesNotExist() {
        // Arrange
        when(bankTransactionRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<BankTransaction> result = bankTransactionService.findById(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(bankTransactionRepository, times(1)).findById(99L);
    }

    @Test
    void findByAccountId_ShouldReturnListOfTransactions() {
        // Arrange
        List<BankTransaction> transactions = Arrays.asList(transaction);
        when(bankTransactionRepository.findByAccountId(1L)).thenReturn(transactions);

        // Act
        List<BankTransaction> result = bankTransactionService.findByAccountId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bankTransactionRepository, times(1)).findByAccountId(1L);
    }

    @Test
    void findByAccountIdAndOrigin_ShouldReturnFilteredTransactions() {
        // Arrange
        List<BankTransaction> transactions = Arrays.asList(transaction);
        when(bankTransactionRepository.findByAccountIdAndOrigin(1L, TransactionOrigin.CARD))
                .thenReturn(transactions);

        // Act
        List<BankTransaction> result = bankTransactionService.findByAccountIdAndOrigin(1L, TransactionOrigin.CARD);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(TransactionOrigin.CARD, result.get(0).getOrigin());
        verify(bankTransactionRepository, times(1)).findByAccountIdAndOrigin(1L, TransactionOrigin.CARD);
    }

    @Test
    void findByCardNumber_ShouldReturnListOfTransactions() {
        // Arrange
        List<BankTransaction> transactions = Arrays.asList(transaction);
        when(bankTransactionRepository.findByCardNumber("1234567890123456")).thenReturn(transactions);

        // Act
        List<BankTransaction> result = bankTransactionService.findByCardNumber("1234567890123456");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("1234567890123456", result.get(0).getCardNumber());
        verify(bankTransactionRepository, times(1)).findByCardNumber("1234567890123456");
    }

    @Test
    void createTransaction_WithoutCardNumber_ShouldCreateTransaction() {
        // Arrange
        when(bankTransactionRepository.save(any(BankTransaction.class))).thenReturn(transaction);
        ArgumentCaptor<BankTransaction> transactionCaptor = ArgumentCaptor.forClass(BankTransaction.class);

        // Act
        bankTransactionService.createTransaction(
                bankAccount,
                new BigDecimal("50.00"),
                "Transfer",
                TransactionType.CREDIT,
                TransactionOrigin.TRANSFER
        );

        // Assert
        verify(bankTransactionRepository, times(1)).save(transactionCaptor.capture());
        BankTransaction captured = transactionCaptor.getValue();
        assertEquals(bankAccount, captured.getBankAccount());
        assertEquals(new BigDecimal("50.00"), captured.getAmount());
        assertEquals("Transfer", captured.getConcept());
        assertEquals(TransactionType.CREDIT, captured.getType());
        assertEquals(TransactionOrigin.TRANSFER, captured.getOrigin());
        assertNull(captured.getCardNumber());
        assertNotNull(captured.getDate());
    }

    @Test
    void createTransaction_WithCardNumber_ShouldCreateTransactionWithCard() {
        // Arrange
        when(bankTransactionRepository.save(any(BankTransaction.class))).thenReturn(transaction);
        ArgumentCaptor<BankTransaction> transactionCaptor = ArgumentCaptor.forClass(BankTransaction.class);

        // Act
        bankTransactionService.createTransaction(
                bankAccount,
                new BigDecimal("100.00"),
                "Card payment",
                TransactionType.DEBIT,
                TransactionOrigin.CARD,
                "9876543210987654"
        );

        // Assert
        verify(bankTransactionRepository, times(1)).save(transactionCaptor.capture());
        BankTransaction captured = transactionCaptor.getValue();
        assertEquals(bankAccount, captured.getBankAccount());
        assertEquals(new BigDecimal("100.00"), captured.getAmount());
        assertEquals("Card payment", captured.getConcept());
        assertEquals(TransactionType.DEBIT, captured.getType());
        assertEquals(TransactionOrigin.CARD, captured.getOrigin());
        assertEquals("9876543210987654", captured.getCardNumber());
        assertNotNull(captured.getDate());
    }

    @Test
    void createTransaction_ShouldSetCurrentDate() {
        // Arrange
        when(bankTransactionRepository.save(any(BankTransaction.class))).thenReturn(transaction);
        ArgumentCaptor<BankTransaction> transactionCaptor = ArgumentCaptor.forClass(BankTransaction.class);

        // Act
        bankTransactionService.createTransaction(
                bankAccount,
                new BigDecimal("75.00"),
                "Test",
                TransactionType.DEBIT,
                TransactionOrigin.TRANSFER
        );

        // Assert
        verify(bankTransactionRepository, times(1)).save(transactionCaptor.capture());
        BankTransaction captured = transactionCaptor.getValue();
        assertNotNull(captured.getDate());
        
        String currentMonth = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM"));
        assertTrue(captured.getDate().contains(currentMonth));
    }
}

