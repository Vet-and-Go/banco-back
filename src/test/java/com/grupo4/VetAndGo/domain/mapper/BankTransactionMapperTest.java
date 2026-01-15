package com.grupo4.VetAndGo.domain.mapper;

import com.grupo4.VetAndGo.domain.model.BankAccount;
import com.grupo4.VetAndGo.domain.model.BankTransaction;
import com.grupo4.VetAndGo.domain.model.TransactionOrigin;
import com.grupo4.VetAndGo.domain.model.TransactionType;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.BankAccountJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.BankTransactionJpaEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BankTransactionMapperTest {

    @Test
    void toDomainBankTransaction_ShouldMapEntityToModel_WhenValidEntityProvided() {
        // Arrange
        BankAccountJpaEntity accountEntity = new BankAccountJpaEntity();
        accountEntity.setId(1L);
        accountEntity.setIban("ES1234567890123456789012");

        BankTransactionJpaEntity entity = new BankTransactionJpaEntity();
        entity.setId(1L);
        entity.setDate("2026-01-15T10:30:00");
        entity.setAmount(new BigDecimal("100.00"));
        entity.setConcept("Test transaction");
        entity.setType(TransactionType.DEBIT);
        entity.setOrigin(TransactionOrigin.CARD);
        entity.setCardNumber("1234567890123456");
        entity.setBankAccount(accountEntity);

        // Act
        BankTransaction result = BankTransactionMapper.toDomainBankTransaction(entity);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("2026-01-15T10:30:00", result.getDate());
        assertEquals(new BigDecimal("100.00"), result.getAmount());
        assertEquals("Test transaction", result.getConcept());
        assertEquals(TransactionType.DEBIT, result.getType());
        assertEquals(TransactionOrigin.CARD, result.getOrigin());
        assertEquals("1234567890123456", result.getCardNumber());
        assertNotNull(result.getBankAccount());
        assertEquals(1L, result.getBankAccount().getId());
    }

    @Test
    void toDomainBankTransaction_ShouldReturnNull_WhenEntityIsNull() {
        // Act
        BankTransaction result = BankTransactionMapper.toDomainBankTransaction(null);

        // Assert
        assertNull(result);
    }

    @Test
    void toDomainBankTransaction_ShouldHandleNullAccount_WhenAccountIsNull() {
        // Arrange
        BankTransactionJpaEntity entity = new BankTransactionJpaEntity();
        entity.setId(1L);
        entity.setDate("2026-01-15T10:30:00");
        entity.setAmount(new BigDecimal("50.00"));
        entity.setConcept("Transaction without account");
        entity.setType(TransactionType.CREDIT);
        entity.setOrigin(TransactionOrigin.TRANSFER);
        entity.setCardNumber(null);
        entity.setBankAccount(null);

        // Act
        BankTransaction result = BankTransactionMapper.toDomainBankTransaction(entity);

        // Assert
        assertNotNull(result);
        assertNull(result.getBankAccount());
        assertNull(result.getCardNumber());
    }

    @Test
    void fromDomainBankTransactiontojpaEntity_ShouldMapModelToEntity_WhenValidModelProvided() {
        // Arrange
        BankAccount account = new BankAccount();
        account.setId(1L);
        account.setIban("ES1234567890123456789012");

        BankTransaction domain = new BankTransaction();
        domain.setId(1L);
        domain.setDate("2026-01-15T14:30:00");
        domain.setAmount(new BigDecimal("200.00"));
        domain.setConcept("Test domain transaction");
        domain.setType(TransactionType.CREDIT);
        domain.setOrigin(TransactionOrigin.CARD);
        domain.setCardNumber("9876543210987654");
        domain.setBankAccount(account);

        // Act
        BankTransactionJpaEntity result = BankTransactionMapper.fromDomainBankTransactiontojpaEntity(domain);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("2026-01-15T14:30:00", result.getDate());
        assertEquals(new BigDecimal("200.00"), result.getAmount());
        assertEquals("Test domain transaction", result.getConcept());
        assertEquals(TransactionType.CREDIT, result.getType());
        assertEquals(TransactionOrigin.CARD, result.getOrigin());
        assertEquals("9876543210987654", result.getCardNumber());
        assertNotNull(result.getBankAccount());
        assertEquals(1L, result.getBankAccount().getId());
    }

    @Test
    void fromDomainBankTransactiontojpaEntity_ShouldReturnNull_WhenModelIsNull() {
        // Act
        BankTransactionJpaEntity result = BankTransactionMapper.fromDomainBankTransactiontojpaEntity(null);

        // Assert
        assertNull(result);
    }

    @Test
    void fromDomainBankTransactiontojpaEntity_ShouldHandleNullAccount_WhenAccountIsNull() {
        // Arrange
        BankTransaction domain = new BankTransaction();
        domain.setId(2L);
        domain.setDate("2026-01-16T08:00:00");
        domain.setAmount(new BigDecimal("75.00"));
        domain.setConcept("Transaction without account");
        domain.setType(TransactionType.DEBIT);
        domain.setOrigin(TransactionOrigin.TRANSFER);
        domain.setCardNumber(null);
        domain.setBankAccount(null);

        // Act
        BankTransactionJpaEntity result = BankTransactionMapper.fromDomainBankTransactiontojpaEntity(domain);

        // Assert
        assertNotNull(result);
        assertNull(result.getBankAccount());
        assertNull(result.getCardNumber());
    }

    @Test
    void toDomainBankTransaction_ShouldHandleDifferentTransactionTypes() {
        // Arrange - CREDIT transaction
        BankTransactionJpaEntity creditEntity = new BankTransactionJpaEntity();
        creditEntity.setId(1L);
        creditEntity.setDate("2026-01-15T10:00:00");
        creditEntity.setAmount(new BigDecimal("100.00"));
        creditEntity.setConcept("Credit");
        creditEntity.setType(TransactionType.CREDIT);
        creditEntity.setOrigin(TransactionOrigin.TRANSFER);

        // Act
        BankTransaction creditResult = BankTransactionMapper.toDomainBankTransaction(creditEntity);

        // Assert
        assertNotNull(creditResult);
        assertEquals(TransactionType.CREDIT, creditResult.getType());
        assertEquals(TransactionOrigin.TRANSFER, creditResult.getOrigin());
    }
}

