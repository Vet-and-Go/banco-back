package com.grupo4.VetAndGo.domain.service.impl;

import com.grupo4.VetAndGo.domain.exception.ValidationException;
import com.grupo4.VetAndGo.domain.model.BankAccount;
import com.grupo4.VetAndGo.domain.model.BankTransaction;
import com.grupo4.VetAndGo.domain.model.CreditCard;
import com.grupo4.VetAndGo.domain.model.TransactionOrigin;
import com.grupo4.VetAndGo.domain.model.TransactionType;
import com.grupo4.VetAndGo.domain.repository.CreditCardRepository;
import com.grupo4.VetAndGo.domain.service.BankTransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditCardServiceImplTest {

    @Mock
    private CreditCardRepository creditCardRepository;

    @Mock
    private BankTransactionService bankTransactionService;

    @InjectMocks
    private CreditCardServiceImpl creditCardService;

    private CreditCard creditCard;
    private BankAccount bankAccount;

    @BeforeEach
    void setUp() {
        bankAccount = new BankAccount();
        bankAccount.setId(1L);
        bankAccount.setIban("ES1234567890123456789012");
        bankAccount.setBalance(new BigDecimal("1000.00"));

        creditCard = new CreditCard();
        creditCard.setId(1L);
        creditCard.setCardNumber("1234567890123456");
        creditCard.setExpirationDate("2026-12");
        creditCard.setCvc("123");
        creditCard.setFullName("John Doe");
        creditCard.setBankAccount(bankAccount);
    }

    @Test
    void findAll_ShouldReturnListOfCreditCards() {
        // Arrange
        List<CreditCard> cards = Arrays.asList(creditCard);
        when(creditCardRepository.findAll()).thenReturn(cards);

        // Act
        List<CreditCard> result = creditCardService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("1234567890123456", result.get(0).getCardNumber());
        verify(creditCardRepository, times(1)).findAll();
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenNoCards() {
        // Arrange
        when(creditCardRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<CreditCard> result = creditCardService.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(creditCardRepository, times(1)).findAll();
    }

    @Test
    void findById_ShouldReturnCreditCard_WhenCardExists() {
        // Arrange
        when(creditCardRepository.findById(1L)).thenReturn(Optional.of(creditCard));

        // Act
        Optional<CreditCard> result = creditCardService.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("1234567890123456", result.get().getCardNumber());
        verify(creditCardRepository, times(1)).findById(1L);
    }

    @Test
    void findById_ShouldReturnEmpty_WhenCardDoesNotExist() {
        // Arrange
        when(creditCardRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<CreditCard> result = creditCardService.findById(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(creditCardRepository, times(1)).findById(99L);
    }

    @Test
    void findByCardNumber_ShouldReturnCreditCard_WhenCardExists() {
        // Arrange
        when(creditCardRepository.findByCardNumber("1234567890123456")).thenReturn(Optional.of(creditCard));

        // Act
        Optional<CreditCard> result = creditCardService.findByCardNumber("1234567890123456");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("1234567890123456", result.get().getCardNumber());
        verify(creditCardRepository, times(1)).findByCardNumber("1234567890123456");
    }

    @Test
    void findByClientId_ShouldReturnListOfCreditCards() {
        // Arrange
        List<CreditCard> cards = Arrays.asList(creditCard);
        when(creditCardRepository.findByClientId(1L)).thenReturn(cards);

        // Act
        List<CreditCard> result = creditCardService.findByClientId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(creditCardRepository, times(1)).findByClientId(1L);
    }

    @Test
    void findByBankAccountId_ShouldReturnListOfCreditCards() {
        // Arrange
        List<CreditCard> cards = Arrays.asList(creditCard);
        when(creditCardRepository.findByBankAccountId(1L)).thenReturn(cards);

        // Act
        List<CreditCard> result = creditCardService.findByBankAccountId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(creditCardRepository, times(1)).findByBankAccountId(1L);
    }

    @Test
    void findTransactionsByCardId_ShouldReturnTransactions_WhenCardExists() {
        // Arrange
        BankTransaction transaction = new BankTransaction();
        transaction.setId(1L);
        transaction.setCardNumber("1234567890123456");
        transaction.setAmount(new BigDecimal("50.00"));

        List<BankTransaction> transactions = Arrays.asList(transaction);
        when(creditCardRepository.findById(1L)).thenReturn(Optional.of(creditCard));
        when(bankTransactionService.findByCardNumber("1234567890123456")).thenReturn(transactions);

        // Act
        List<BankTransaction> result = creditCardService.findTransactionsByCardId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("1234567890123456", result.get(0).getCardNumber());
        verify(creditCardRepository, times(1)).findById(1L);
        verify(bankTransactionService, times(1)).findByCardNumber("1234567890123456");
    }

    @Test
    void findTransactionsByCardId_ShouldThrowException_WhenCardDoesNotExist() {
        // Arrange
        when(creditCardRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ValidationException.class, () -> creditCardService.findTransactionsByCardId(99L));
        verify(creditCardRepository, times(1)).findById(99L);
        verify(bankTransactionService, never()).findByCardNumber(any());
    }

    @Test
    void calculateMonthlySpending_ShouldReturnTotalDebitAmount() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        BankTransaction debitTransaction1 = new BankTransaction();
        debitTransaction1.setId(1L);
        debitTransaction1.setType(TransactionType.DEBIT);
        debitTransaction1.setAmount(new BigDecimal("100.00"));
        debitTransaction1.setDate(now.minusDays(5).toString());

        BankTransaction debitTransaction2 = new BankTransaction();
        debitTransaction2.setId(2L);
        debitTransaction2.setType(TransactionType.DEBIT);
        debitTransaction2.setAmount(new BigDecimal("50.00"));
        debitTransaction2.setDate(now.minusDays(10).toString());

        BankTransaction creditTransaction = new BankTransaction();
        creditTransaction.setId(3L);
        creditTransaction.setType(TransactionType.CREDIT);
        creditTransaction.setAmount(new BigDecimal("200.00"));
        creditTransaction.setDate(now.minusDays(3).toString());

        List<BankTransaction> transactions = Arrays.asList(debitTransaction1, debitTransaction2, creditTransaction);

        when(creditCardRepository.findById(1L)).thenReturn(Optional.of(creditCard));
        when(bankTransactionService.findByCardNumber("1234567890123456")).thenReturn(transactions);

        // Act
        BigDecimal result = creditCardService.calculateMonthlySpending(1L);

        // Assert
        assertEquals(new BigDecimal("150.00"), result);
        verify(creditCardRepository, times(1)).findById(1L);
    }

    @Test
    void calculateMonthlySpending_ShouldReturnZero_WhenNoDebitTransactions() {
        // Arrange
        when(creditCardRepository.findById(1L)).thenReturn(Optional.of(creditCard));
        when(bankTransactionService.findByCardNumber("1234567890123456")).thenReturn(Collections.emptyList());

        // Act
        BigDecimal result = creditCardService.calculateMonthlySpending(1L);

        // Assert
        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    void calculateMonthlySpending_ShouldExcludeOldTransactions() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        BankTransaction recentTransaction = new BankTransaction();
        recentTransaction.setId(1L);
        recentTransaction.setType(TransactionType.DEBIT);
        recentTransaction.setAmount(new BigDecimal("100.00"));
        recentTransaction.setDate(now.minusDays(5).toString());

        BankTransaction oldTransaction = new BankTransaction();
        oldTransaction.setId(2L);
        oldTransaction.setType(TransactionType.DEBIT);
        oldTransaction.setAmount(new BigDecimal("500.00"));
        oldTransaction.setDate(now.minusMonths(2).toString());

        List<BankTransaction> transactions = Arrays.asList(recentTransaction, oldTransaction);

        when(creditCardRepository.findById(1L)).thenReturn(Optional.of(creditCard));
        when(bankTransactionService.findByCardNumber("1234567890123456")).thenReturn(transactions);

        // Act
        BigDecimal result = creditCardService.calculateMonthlySpending(1L);

        // Assert
        assertEquals(new BigDecimal("100.00"), result);
    }

    @Test
    void calculateMonthlySpending_ShouldHandleInvalidDateFormat() {
        // Arrange
        BankTransaction invalidDateTransaction = new BankTransaction();
        invalidDateTransaction.setId(1L);
        invalidDateTransaction.setType(TransactionType.DEBIT);
        invalidDateTransaction.setAmount(new BigDecimal("100.00"));
        invalidDateTransaction.setDate("invalid-date");

        List<BankTransaction> transactions = Arrays.asList(invalidDateTransaction);

        when(creditCardRepository.findById(1L)).thenReturn(Optional.of(creditCard));
        when(bankTransactionService.findByCardNumber("1234567890123456")).thenReturn(transactions);

        // Act
        BigDecimal result = creditCardService.calculateMonthlySpending(1L);

        // Assert
        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    void calculateMonthlySpending_ShouldThrowException_WhenCardDoesNotExist() {
        // Arrange
        when(creditCardRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ValidationException.class, () -> creditCardService.calculateMonthlySpending(99L));
        verify(creditCardRepository, times(1)).findById(99L);
    }
}
