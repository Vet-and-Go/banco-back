package com.grupo4.VetAndGo.domain.mapper;

import com.grupo4.VetAndGo.domain.model.BankAccount;
import com.grupo4.VetAndGo.domain.model.Client;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.BankAccountJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.ClientJpaEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountMapperTest {

    @Test
    void toDomainBankAccount_ShouldMapEntityToModel_WhenValidEntityProvided() {
        // Arrange
        ClientJpaEntity clientEntity = new ClientJpaEntity();
        clientEntity.setId(1L);
        clientEntity.setFirstName("John");

        BankAccountJpaEntity entity = new BankAccountJpaEntity();
        entity.setId(1L);
        entity.setBalance(new BigDecimal("1000.00"));
        entity.setIban("ES1234567890123456789012");
        entity.setClient(clientEntity);

        // Act
        BankAccount result = BankAccountMapper.toDomainBankAccount(entity);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(new BigDecimal("1000.00"), result.getBalance());
        assertEquals("ES1234567890123456789012", result.getIban());
        assertNotNull(result.getClient());
        assertEquals(1L, result.getClient().getId());
        assertEquals("John", result.getClient().getFirstName());
    }

    @Test
    void toDomainBankAccount_ShouldReturnNull_WhenEntityIsNull() {
        // Act
        BankAccount result = BankAccountMapper.toDomainBankAccount(null);

        // Assert
        assertNull(result);
    }

    @Test
    void toDomainBankAccount_ShouldHandleNullClient_WhenClientIsNull() {
        // Arrange
        BankAccountJpaEntity entity = new BankAccountJpaEntity();
        entity.setId(1L);
        entity.setBalance(new BigDecimal("500.00"));
        entity.setIban("ES9876543210987654321098");
        entity.setClient(null);

        // Act
        BankAccount result = BankAccountMapper.toDomainBankAccount(entity);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertNull(result.getClient());
    }

    @Test
    void fromDomainAccounttojpaEntity_ShouldMapModelToEntity_WhenValidModelProvided() {
        // Arrange
        Client client = new Client();
        client.setId(1L);
        client.setFirstName("John");

        BankAccount domain = new BankAccount();
        domain.setId(1L);
        domain.setBalance(new BigDecimal("1500.00"));
        domain.setIban("ES1111222233334444555566");
        domain.setClient(client);

        // Act
        BankAccountJpaEntity result = BankAccountMapper.fromDomainAccounttojpaEntity(domain);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(new BigDecimal("1500.00"), result.getBalance());
        assertEquals("ES1111222233334444555566", result.getIban());
        assertNotNull(result.getClient());
        assertEquals(1L, result.getClient().getId());
    }

    @Test
    void fromDomainAccounttojpaEntity_ShouldReturnNull_WhenModelIsNull() {
        // Act
        BankAccountJpaEntity result = BankAccountMapper.fromDomainAccounttojpaEntity(null);

        // Assert
        assertNull(result);
    }

    @Test
    void fromDomainAccounttojpaEntity_ShouldHandleNullClient_WhenClientIsNull() {
        // Arrange
        BankAccount domain = new BankAccount();
        domain.setId(1L);
        domain.setBalance(new BigDecimal("750.00"));
        domain.setIban("ES5555666677778888999900");
        domain.setClient(null);

        // Act
        BankAccountJpaEntity result = BankAccountMapper.fromDomainAccounttojpaEntity(domain);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertNull(result.getClient());
    }

    @Test
    void toDomainBankAccount_ShouldHandleZeroBalance_WhenBalanceIsZero() {
        // Arrange
        ClientJpaEntity clientEntity = new ClientJpaEntity();
        clientEntity.setId(2L);

        BankAccountJpaEntity entity = new BankAccountJpaEntity();
        entity.setId(2L);
        entity.setBalance(BigDecimal.ZERO);
        entity.setIban("ES0000000000000000000000");
        entity.setClient(clientEntity);

        // Act
        BankAccount result = BankAccountMapper.toDomainBankAccount(entity);

        // Assert
        assertNotNull(result);
        assertEquals(BigDecimal.ZERO, result.getBalance());
    }
}

