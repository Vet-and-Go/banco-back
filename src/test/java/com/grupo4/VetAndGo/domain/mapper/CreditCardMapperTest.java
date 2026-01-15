package com.grupo4.VetAndGo.domain.mapper;

import com.grupo4.VetAndGo.domain.model.BankAccount;
import com.grupo4.VetAndGo.domain.model.CreditCard;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.BankAccountJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.CreditCardJpaEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CreditCardMapperTest {

    @Test
    void toDomainCreditCard_ShouldMapEntityToModel_WhenValidEntityProvided() {
        // Arrange
        BankAccountJpaEntity accountEntity = new BankAccountJpaEntity();
        accountEntity.setId(1L);
        accountEntity.setIban("ES1234567890123456789012");
        accountEntity.setBalance(new BigDecimal("1000.00"));

        CreditCardJpaEntity entity = new CreditCardJpaEntity();
        entity.setId(1L);
        entity.setCardNumber("1234567890123456");
        entity.setExpirationDate("2026-12");
        entity.setCvc("123");
        entity.setFullName("John Doe Smith");
        entity.setBankAccount(accountEntity);

        // Act
        CreditCard result = CreditCardMapper.toDomainCreditCard(entity);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("1234567890123456", result.getCardNumber());
        assertEquals("2026-12", result.getExpirationDate());
        assertEquals("123", result.getCvc());
        assertEquals("John Doe Smith", result.getFullName());
        assertNotNull(result.getBankAccount());
        assertEquals(1L, result.getBankAccount().getId());
        assertEquals("ES1234567890123456789012", result.getBankAccount().getIban());
        assertEquals(new BigDecimal("1000.00"), result.getBankAccount().getBalance());
    }

    @Test
    void toDomainCreditCard_ShouldReturnNull_WhenEntityIsNull() {
        // Act
        CreditCard result = CreditCardMapper.toDomainCreditCard(null);

        // Assert
        assertNull(result);
    }

    @Test
    void toDomainCreditCard_ShouldHandleNullAccount_WhenAccountIsNull() {
        // Arrange
        CreditCardJpaEntity entity = new CreditCardJpaEntity();
        entity.setId(1L);
        entity.setCardNumber("9876543210987654");
        entity.setExpirationDate("2025-06");
        entity.setCvc("456");
        entity.setFullName("Jane Doe");
        entity.setBankAccount(null);

        // Act
        CreditCard result = CreditCardMapper.toDomainCreditCard(entity);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertNull(result.getBankAccount());
    }

    @Test
    void fromDomainCreditCardtojpaEntity_ShouldMapModelToEntity_WhenValidModelProvided() {
        // Arrange
        BankAccount account = new BankAccount();
        account.setId(1L);
        account.setIban("ES9876543210987654321098");
        account.setBalance(new BigDecimal("2000.00"));

        CreditCard domain = new CreditCard();
        domain.setId(1L);
        domain.setCardNumber("5555666677778888");
        domain.setExpirationDate("2027-03");
        domain.setCvc("789");
        domain.setFullName("Test User");
        domain.setBankAccount(account);

        // Act
        CreditCardJpaEntity result = CreditCardMapper.fromDomainCreditCardtojpaEntity(domain);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("5555666677778888", result.getCardNumber());
        assertEquals("2027-03", result.getExpirationDate());
        assertEquals("789", result.getCvc());
        assertEquals("Test User", result.getFullName());
        assertNotNull(result.getBankAccount());
        assertEquals(1L, result.getBankAccount().getId());
    }

    @Test
    void fromDomainCreditCardtojpaEntity_ShouldReturnNull_WhenModelIsNull() {
        // Act
        CreditCardJpaEntity result = CreditCardMapper.fromDomainCreditCardtojpaEntity(null);

        // Assert
        assertNull(result);
    }

    @Test
    void fromDomainCreditCardtojpaEntity_ShouldHandleNullAccount_WhenAccountIsNull() {
        // Arrange
        CreditCard domain = new CreditCard();
        domain.setId(2L);
        domain.setCardNumber("1111222233334444");
        domain.setExpirationDate("2028-01");
        domain.setCvc("999");
        domain.setFullName("Another User");
        domain.setBankAccount(null);

        // Act
        CreditCardJpaEntity result = CreditCardMapper.fromDomainCreditCardtojpaEntity(domain);

        // Assert
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertNull(result.getBankAccount());
    }

    @Test
    void toDomainCreditCard_ShouldHandleNullFields_WhenEntityHasNullFields() {
        // Arrange
        CreditCardJpaEntity entity = new CreditCardJpaEntity();
        entity.setId(3L);
        entity.setCardNumber("0000111122223333");
        entity.setExpirationDate(null);
        entity.setCvc(null);
        entity.setFullName(null);
        entity.setBankAccount(null);

        // Act
        CreditCard result = CreditCardMapper.toDomainCreditCard(entity);

        // Assert
        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals("0000111122223333", result.getCardNumber());
        assertNull(result.getExpirationDate());
        assertNull(result.getCvc());
        assertNull(result.getFullName());
    }

    @Test
    void toDomainCreditCard_ShouldMapAccountWithAllFields_WhenAccountHasAllData() {
        // Arrange
        BankAccountJpaEntity accountEntity = new BankAccountJpaEntity();
        accountEntity.setId(5L);
        accountEntity.setIban("ES5555666677778888999900");
        accountEntity.setBalance(new BigDecimal("5000.50"));

        CreditCardJpaEntity entity = new CreditCardJpaEntity();
        entity.setId(5L);
        entity.setCardNumber("4444555566667777");
        entity.setExpirationDate("2029-12");
        entity.setCvc("321");
        entity.setFullName("Complete User");
        entity.setBankAccount(accountEntity);

        // Act
        CreditCard result = CreditCardMapper.toDomainCreditCard(entity);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getBankAccount());
        assertEquals(5L, result.getBankAccount().getId());
        assertEquals("ES5555666677778888999900", result.getBankAccount().getIban());
        assertEquals(new BigDecimal("5000.50"), result.getBankAccount().getBalance());
    }
}

