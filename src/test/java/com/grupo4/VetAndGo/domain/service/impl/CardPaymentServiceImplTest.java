package com.grupo4.VetAndGo.domain.service.impl;

import com.grupo4.VetAndGo.domain.exception.BussinesException;
import com.grupo4.VetAndGo.domain.model.*;
import com.grupo4.VetAndGo.domain.repository.BankAccountRepository;
import com.grupo4.VetAndGo.domain.repository.CreditCardRepository;
import com.grupo4.VetAndGo.domain.service.BankTransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardPaymentServiceImplTest {

    @Mock
    private CreditCardRepository creditCardRepository;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private BankTransactionService bankTransactionService;

    @InjectMocks
    private CardPaymentServiceImpl cardPaymentService;

    private CardPayment payment;
    private CreditCard creditCard;
    private BankAccount fromAccount;
    private BankAccount toAccount;
    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client(1L, "user1", "pass1", "John", "Doe", "Smith", "12345678A");
        fromAccount = new BankAccount(1L, new BigDecimal("1000.00"), "ES1111111111111111111111", client);
        toAccount = new BankAccount(2L, new BigDecimal("500.00"), "ES2222222222222222222222", client);
        creditCard = new CreditCard(1L, "1234567890123456", "2026-12", "123", "John Doe Smith", fromAccount);

        payment = CardPayment.builder()
                .destinationIban("ES2222222222222222222222")
                .amount(new BigDecimal("100.00"))
                .concept("Test payment")
                .cardNumber("1234567890123456")
                .cardExpirationDate("2026-12")
                .cardCvc("123")
                .cardHolderName("John Doe Smith")
                .build();
    }

    @Test
    void processPayment_ShouldProcessSuccessfully_WhenValidPayment() {
        // Arrange
        when(creditCardRepository.findByCardNumber("1234567890123456")).thenReturn(Optional.of(creditCard));
        when(bankAccountRepository.findByIban("ES2222222222222222222222")).thenReturn(Optional.of(toAccount));
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(fromAccount);
        doNothing().when(bankTransactionService).createTransaction(any(), any(), any(), any(), any(), any());
        doNothing().when(bankTransactionService).createTransaction(any(), any(), any(), any(), any());

        // Act
        cardPaymentService.processPayment(payment);

        // Assert
        assertEquals(new BigDecimal("900.00"), fromAccount.getBalance());
        assertEquals(new BigDecimal("600.00"), toAccount.getBalance());
        verify(creditCardRepository, times(1)).findByCardNumber("1234567890123456");
        verify(bankAccountRepository, times(1)).findByIban("ES2222222222222222222222");
        verify(bankAccountRepository, times(2)).save(any(BankAccount.class));
        verify(bankTransactionService, times(1)).createTransaction(eq(fromAccount), any(), any(), eq(TransactionType.DEBIT), eq(TransactionOrigin.CARD), any());
        verify(bankTransactionService, times(1)).createTransaction(eq(toAccount), any(), any(), eq(TransactionType.CREDIT), eq(TransactionOrigin.CARD));
    }

    @Test
    void processPayment_ShouldThrowException_WhenInvalidIbanFormat() {
        // Arrange
        payment.setDestinationIban("INVALID");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> cardPaymentService.processPayment(payment));
        assertTrue(exception.getMessage().contains("Invalid IBAN format"));
        verify(creditCardRepository, never()).findByCardNumber(any());
    }

    @Test
    void processPayment_ShouldThrowException_WhenAmountIsZero() {
        // Arrange
        payment.setAmount(BigDecimal.ZERO);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> cardPaymentService.processPayment(payment));
        assertTrue(exception.getMessage().contains("Amount must be greater than zero"));
        verify(creditCardRepository, never()).findByCardNumber(any());
    }

    @Test
    void processPayment_ShouldThrowException_WhenAmountIsNegative() {
        // Arrange
        payment.setAmount(new BigDecimal("-50.00"));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> cardPaymentService.processPayment(payment));
        assertTrue(exception.getMessage().contains("Amount must be greater than zero"));
    }

    @Test
    void processPayment_ShouldThrowException_WhenConceptIsTooShort() {
        // Arrange
        payment.setConcept("ab");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> cardPaymentService.processPayment(payment));
        assertTrue(exception.getMessage().contains("Concept must have at least 3 characters"));
    }

    @Test
    void processPayment_ShouldThrowException_WhenCardNotFound() {
        // Arrange
        when(creditCardRepository.findByCardNumber("1234567890123456")).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> cardPaymentService.processPayment(payment));
        assertTrue(exception.getMessage().contains("Card not found"));
        verify(creditCardRepository, times(1)).findByCardNumber("1234567890123456");
        verify(bankAccountRepository, never()).findByIban(any());
    }

    @Test
    void processPayment_ShouldThrowException_WhenCardIsExpired() {
        // Arrange
        CreditCard expiredCard = new CreditCard(1L, "1234567890123456", "2020-01", "123", "John Doe Smith", fromAccount);
        when(creditCardRepository.findByCardNumber("1234567890123456")).thenReturn(Optional.of(expiredCard));

        payment.setCardExpirationDate("2020-01");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> cardPaymentService.processPayment(payment));
        assertTrue(exception.getMessage().contains("expired"));
    }

    @Test
    void processPayment_ShouldThrowException_WhenCardNotLinkedToAccount() {
        // Arrange
        CreditCard cardWithoutAccount = new CreditCard(1L, "1234567890123456", "2026-12", "123", "John Doe Smith", null);
        when(creditCardRepository.findByCardNumber("1234567890123456")).thenReturn(Optional.of(cardWithoutAccount));

        // Act & Assert
        BussinesException exception = assertThrows(BussinesException.class,
            () -> cardPaymentService.processPayment(payment));
        assertTrue(exception.getMessage().contains("not linked to any active bank account"));
    }

    @Test
    void processPayment_ShouldThrowException_WhenDestinationIbanNotFound() {
        // Arrange
        when(creditCardRepository.findByCardNumber("1234567890123456")).thenReturn(Optional.of(creditCard));
        when(bankAccountRepository.findByIban("ES2222222222222222222222")).thenReturn(Optional.empty());

        // Act & Assert
        BussinesException exception = assertThrows(BussinesException.class,
            () -> cardPaymentService.processPayment(payment));
        assertTrue(exception.getMessage().contains("Destination account IBAN not found"));
        verify(creditCardRepository, times(1)).findByCardNumber("1234567890123456");
        verify(bankAccountRepository, times(1)).findByIban("ES2222222222222222222222");
    }

    @Test
    void processPayment_ShouldThrowException_WhenInsufficientFunds() {
        // Arrange
        fromAccount.setBalance(new BigDecimal("50.00"));
        when(creditCardRepository.findByCardNumber("1234567890123456")).thenReturn(Optional.of(creditCard));
        when(bankAccountRepository.findByIban("ES2222222222222222222222")).thenReturn(Optional.of(toAccount));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> cardPaymentService.processPayment(payment));
        assertTrue(exception.getMessage().contains("Insufficient funds"));
        verify(bankAccountRepository, never()).save(any());
    }

    @Test
    void processPayment_ShouldHandleNullBalance_WhenFromAccountBalanceIsNull() {
        // Arrange
        fromAccount.setBalance(null);
        when(creditCardRepository.findByCardNumber("1234567890123456")).thenReturn(Optional.of(creditCard));
        when(bankAccountRepository.findByIban("ES2222222222222222222222")).thenReturn(Optional.of(toAccount));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> cardPaymentService.processPayment(payment));
        assertTrue(exception.getMessage().contains("Insufficient funds"));
    }

    @Test
    void processPayment_ShouldHandleNullBalance_WhenToAccountBalanceIsNull() {
        // Arrange
        toAccount.setBalance(null);
        when(creditCardRepository.findByCardNumber("1234567890123456")).thenReturn(Optional.of(creditCard));
        when(bankAccountRepository.findByIban("ES2222222222222222222222")).thenReturn(Optional.of(toAccount));
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(fromAccount);
        doNothing().when(bankTransactionService).createTransaction(any(), any(), any(), any(), any(), any());
        doNothing().when(bankTransactionService).createTransaction(any(), any(), any(), any(), any());

        // Act
        cardPaymentService.processPayment(payment);

        // Assert
        assertEquals(new BigDecimal("100.00"), toAccount.getBalance());
        verify(bankAccountRepository, times(2)).save(any(BankAccount.class));
    }
}

