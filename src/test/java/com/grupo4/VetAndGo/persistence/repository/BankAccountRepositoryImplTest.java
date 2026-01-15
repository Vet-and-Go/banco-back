package com.grupo4.VetAndGo.persistence.repository;

import com.grupo4.VetAndGo.domain.model.BankAccount;
import com.grupo4.VetAndGo.persistence.dao.jpa.BankAccountJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.BankAccountJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.ClientJpaEntity;
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
class BankAccountRepositoryImplTest {

    @Mock
    private BankAccountJpaDao bankAccountJpaDao;

    @InjectMocks
    private BankAccountRepositoryImpl bankAccountRepository;

    private BankAccountJpaEntity accountEntity;
    private BankAccount account;
    private ClientJpaEntity clientEntity;

    @BeforeEach
    void setUp() {
        clientEntity = new ClientJpaEntity();
        clientEntity.setId(1L);
        clientEntity.setFirstName("John");

        accountEntity = new BankAccountJpaEntity();
        accountEntity.setId(1L);
        accountEntity.setBalance(new BigDecimal("1000.00"));
        accountEntity.setIban("ES1234567890123456789012");
        accountEntity.setClient(clientEntity);

        account = new BankAccount();
        account.setId(1L);
        account.setBalance(new BigDecimal("1000.00"));
        account.setIban("ES1234567890123456789012");
    }

    @Test
    void findAll_ShouldReturnListOfBankAccounts() {
        // Arrange
        when(bankAccountJpaDao.findAll()).thenReturn(Arrays.asList(accountEntity));

        // Act
        List<BankAccount> result = bankAccountRepository.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ES1234567890123456789012", result.get(0).getIban());
        verify(bankAccountJpaDao, times(1)).findAll();
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenNoAccounts() {
        // Arrange
        when(bankAccountJpaDao.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<BankAccount> result = bankAccountRepository.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(bankAccountJpaDao, times(1)).findAll();
    }

    @Test
    void findById_ShouldReturnBankAccount_WhenAccountExists() {
        // Arrange
        when(bankAccountJpaDao.findById(1L)).thenReturn(Optional.of(accountEntity));

        // Act
        Optional<BankAccount> result = bankAccountRepository.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("ES1234567890123456789012", result.get().getIban());
        assertEquals(new BigDecimal("1000.00"), result.get().getBalance());
        verify(bankAccountJpaDao, times(1)).findById(1L);
    }

    @Test
    void findById_ShouldReturnEmpty_WhenAccountDoesNotExist() {
        // Arrange
        when(bankAccountJpaDao.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<BankAccount> result = bankAccountRepository.findById(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(bankAccountJpaDao, times(1)).findById(99L);
    }

    @Test
    void findByIban_ShouldReturnBankAccount_WhenAccountExists() {
        // Arrange
        when(bankAccountJpaDao.findByIban("ES1234567890123456789012")).thenReturn(Optional.of(accountEntity));

        // Act
        Optional<BankAccount> result = bankAccountRepository.findByIban("ES1234567890123456789012");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("ES1234567890123456789012", result.get().getIban());
        verify(bankAccountJpaDao, times(1)).findByIban("ES1234567890123456789012");
    }

    @Test
    void findByIban_ShouldReturnEmpty_WhenAccountDoesNotExist() {
        // Arrange
        when(bankAccountJpaDao.findByIban("ES9999999999999999999999")).thenReturn(Optional.empty());

        // Act
        Optional<BankAccount> result = bankAccountRepository.findByIban("ES9999999999999999999999");

        // Assert
        assertFalse(result.isPresent());
        verify(bankAccountJpaDao, times(1)).findByIban("ES9999999999999999999999");
    }

    @Test
    void findByClientId_ShouldReturnListOfBankAccounts() {
        // Arrange
        BankAccountJpaEntity account2 = new BankAccountJpaEntity();
        account2.setId(2L);
        account2.setIban("ES9876543210987654321098");
        account2.setBalance(new BigDecimal("2000.00"));
        account2.setClient(clientEntity);

        when(bankAccountJpaDao.findByClientId(1L)).thenReturn(Arrays.asList(accountEntity, account2));

        // Act
        List<BankAccount> result = bankAccountRepository.findByClientId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("ES1234567890123456789012", result.get(0).getIban());
        assertEquals("ES9876543210987654321098", result.get(1).getIban());
        verify(bankAccountJpaDao, times(1)).findByClientId(1L);
    }

    @Test
    void findByClientId_ShouldReturnEmptyList_WhenClientHasNoAccounts() {
        // Arrange
        when(bankAccountJpaDao.findByClientId(99L)).thenReturn(Collections.emptyList());

        // Act
        List<BankAccount> result = bankAccountRepository.findByClientId(99L);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(bankAccountJpaDao, times(1)).findByClientId(99L);
    }

    @Test
    void save_ShouldInsertNewAccount_WhenAccountIdIsNull() {
        // Arrange
        BankAccount newAccount = new BankAccount();
        newAccount.setIban("ES1111222233334444555566");
        newAccount.setBalance(new BigDecimal("500.00"));

        BankAccountJpaEntity savedEntity = new BankAccountJpaEntity();
        savedEntity.setId(2L);
        savedEntity.setIban("ES1111222233334444555566");
        savedEntity.setBalance(new BigDecimal("500.00"));

        when(bankAccountJpaDao.insert(any(BankAccountJpaEntity.class))).thenReturn(savedEntity);

        // Act
        BankAccount result = bankAccountRepository.save(newAccount);

        // Assert
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("ES1111222233334444555566", result.getIban());
        verify(bankAccountJpaDao, times(1)).insert(any(BankAccountJpaEntity.class));
        verify(bankAccountJpaDao, never()).update(any(BankAccountJpaEntity.class));
    }

    @Test
    void save_ShouldUpdateExistingAccount_WhenAccountIdIsNotNull() {
        // Arrange
        when(bankAccountJpaDao.update(any(BankAccountJpaEntity.class))).thenReturn(accountEntity);

        // Act
        BankAccount result = bankAccountRepository.save(account);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("ES1234567890123456789012", result.getIban());
        verify(bankAccountJpaDao, times(1)).update(any(BankAccountJpaEntity.class));
        verify(bankAccountJpaDao, never()).insert(any(BankAccountJpaEntity.class));
    }



    @Test
    void save_ShouldUpdateBalance_WhenBalanceChanges() {
        // Arrange
        account.setBalance(new BigDecimal("1500.00"));
        accountEntity.setBalance(new BigDecimal("1500.00"));
        when(bankAccountJpaDao.update(any(BankAccountJpaEntity.class))).thenReturn(accountEntity);

        // Act
        BankAccount result = bankAccountRepository.save(account);

        // Assert
        assertNotNull(result);
        assertEquals(new BigDecimal("1500.00"), result.getBalance());
        verify(bankAccountJpaDao, times(1)).update(any(BankAccountJpaEntity.class));
    }
}

