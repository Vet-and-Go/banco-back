package com.grupo4.VetAndGo.persistence.repository;

import com.grupo4.VetAndGo.domain.model.CreditCard;
import com.grupo4.VetAndGo.persistence.dao.jpa.CreditCardJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.BankAccountJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.CreditCardJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditCardRepositoryImplTest {

    @Mock
    private CreditCardJpaDao creditCardJpaDao;

    @InjectMocks
    private CreditCardRepositoryImpl creditCardRepository;

    private CreditCardJpaEntity cardEntity;
    private CreditCard card;
    private BankAccountJpaEntity accountEntity;

    @BeforeEach
    void setUp() {
        accountEntity = new BankAccountJpaEntity();
        accountEntity.setId(1L);
        accountEntity.setIban("ES1234567890123456789012");
        accountEntity.setBalance(new BigDecimal("1000.00"));

        cardEntity = new CreditCardJpaEntity();
        cardEntity.setId(1L);
        cardEntity.setCardNumber("1234567890123456");
        cardEntity.setExpirationDate("2026-12");
        cardEntity.setCvc("123");
        cardEntity.setFullName("John Doe");
        cardEntity.setBankAccount(accountEntity);

        card = new CreditCard();
        card.setId(1L);
        card.setCardNumber("1234567890123456");
        card.setExpirationDate("2026-12");
        card.setCvc("123");
        card.setFullName("John Doe");
    }

    @Test
    void findAll_ShouldReturnListOfCreditCards() {
        // Arrange
        when(creditCardJpaDao.findAll()).thenReturn(Arrays.asList(cardEntity));

        // Act
        List<CreditCard> result = creditCardRepository.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("1234567890123456", result.get(0).getCardNumber());
        verify(creditCardJpaDao, times(1)).findAll();
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenNoCards() {
        // Arrange
        when(creditCardJpaDao.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<CreditCard> result = creditCardRepository.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(creditCardJpaDao, times(1)).findAll();
    }

    @Test
    void findById_ShouldReturnCreditCard_WhenCardExists() {
        // Arrange
        when(creditCardJpaDao.findById(1L)).thenReturn(Optional.of(cardEntity));

        // Act
        Optional<CreditCard> result = creditCardRepository.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("1234567890123456", result.get().getCardNumber());
        assertEquals("John Doe", result.get().getFullName());
        verify(creditCardJpaDao, times(1)).findById(1L);
    }

    @Test
    void findById_ShouldReturnEmpty_WhenCardDoesNotExist() {
        // Arrange
        when(creditCardJpaDao.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<CreditCard> result = creditCardRepository.findById(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(creditCardJpaDao, times(1)).findById(99L);
    }

    @Test
    void findByCardNumber_ShouldReturnCreditCard_WhenCardExists() {
        // Arrange
        when(creditCardJpaDao.findByCardNumber("1234567890123456")).thenReturn(Optional.of(cardEntity));

        // Act
        Optional<CreditCard> result = creditCardRepository.findByCardNumber("1234567890123456");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("1234567890123456", result.get().getCardNumber());
        verify(creditCardJpaDao, times(1)).findByCardNumber("1234567890123456");
    }

    @Test
    void findByCardNumber_ShouldReturnEmpty_WhenCardDoesNotExist() {
        // Arrange
        when(creditCardJpaDao.findByCardNumber("9999999999999999")).thenReturn(Optional.empty());

        // Act
        Optional<CreditCard> result = creditCardRepository.findByCardNumber("9999999999999999");

        // Assert
        assertFalse(result.isPresent());
        verify(creditCardJpaDao, times(1)).findByCardNumber("9999999999999999");
    }

    @Test
    void findByClientId_ShouldReturnListOfCreditCards() {
        // Arrange
        CreditCardJpaEntity card2 = new CreditCardJpaEntity();
        card2.setId(2L);
        card2.setCardNumber("9876543210987654");
        card2.setExpirationDate("2025-06");
        card2.setCvc("456");
        card2.setFullName("Jane Smith");
        card2.setBankAccount(accountEntity);

        when(creditCardJpaDao.findByClientId(1L)).thenReturn(Arrays.asList(cardEntity, card2));

        // Act
        List<CreditCard> result = creditCardRepository.findByClientId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("1234567890123456", result.get(0).getCardNumber());
        assertEquals("9876543210987654", result.get(1).getCardNumber());
        verify(creditCardJpaDao, times(1)).findByClientId(1L);
    }

    @Test
    void findByClientId_ShouldReturnEmptyList_WhenClientHasNoCards() {
        // Arrange
        when(creditCardJpaDao.findByClientId(99L)).thenReturn(Collections.emptyList());

        // Act
        List<CreditCard> result = creditCardRepository.findByClientId(99L);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(creditCardJpaDao, times(1)).findByClientId(99L);
    }

    @Test
    void findByBankAccountId_ShouldReturnListOfCreditCards() {
        // Arrange
        when(creditCardJpaDao.findByBankAccountId(1L)).thenReturn(Arrays.asList(cardEntity));

        // Act
        List<CreditCard> result = creditCardRepository.findByBankAccountId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("1234567890123456", result.get(0).getCardNumber());
        verify(creditCardJpaDao, times(1)).findByBankAccountId(1L);
    }

    @Test
    void findByBankAccountId_ShouldReturnEmptyList_WhenAccountHasNoCards() {
        // Arrange
        when(creditCardJpaDao.findByBankAccountId(99L)).thenReturn(Collections.emptyList());

        // Act
        List<CreditCard> result = creditCardRepository.findByBankAccountId(99L);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(creditCardJpaDao, times(1)).findByBankAccountId(99L);
    }

    @Test
    void findByCardNumber_ShouldHandleSpecialCharacters() {
        // Arrange
        when(creditCardJpaDao.findByCardNumber("1234-5678-9012-3456")).thenReturn(Optional.empty());

        // Act
        Optional<CreditCard> result = creditCardRepository.findByCardNumber("1234-5678-9012-3456");

        // Assert
        assertFalse(result.isPresent());
        verify(creditCardJpaDao, times(1)).findByCardNumber("1234-5678-9012-3456");
    }
}
