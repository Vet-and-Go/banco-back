package com.grupo4.VetAndGo.persistence.dao.jpa.impl;

import com.grupo4.VetAndGo.persistence.dao.jpa.entity.BankAccountJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.ClientJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankAccountJpaDaoImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<BankAccountJpaEntity> typedQuery;

    @Mock
    private TypedQuery<Long> longTypedQuery;

    @InjectMocks
    private BankAccountJpaDaoImpl bankAccountJpaDao;

    private BankAccountJpaEntity accountEntity;
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
    }

    @Test
    void findAll_ShouldReturnListOfBankAccounts() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(BankAccountJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Arrays.asList(accountEntity));

        // Act
        List<BankAccountJpaEntity> result = bankAccountJpaDao.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ES1234567890123456789012", result.get(0).getIban());
        verify(entityManager, times(1)).createQuery(contains("SELECT b FROM BankAccountJpaEntity b"), eq(BankAccountJpaEntity.class));
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenNoAccounts() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(BankAccountJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        // Act
        List<BankAccountJpaEntity> result = bankAccountJpaDao.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findById_ShouldReturnBankAccount_WhenAccountExists() {
        // Arrange
        when(entityManager.find(BankAccountJpaEntity.class, 1L)).thenReturn(accountEntity);

        // Act
        Optional<BankAccountJpaEntity> result = bankAccountJpaDao.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("ES1234567890123456789012", result.get().getIban());
        verify(entityManager, times(1)).find(BankAccountJpaEntity.class, 1L);
    }

    @Test
    void findById_ShouldReturnEmpty_WhenAccountDoesNotExist() {
        // Arrange
        when(entityManager.find(BankAccountJpaEntity.class, 99L)).thenReturn(null);

        // Act
        Optional<BankAccountJpaEntity> result = bankAccountJpaDao.findById(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(entityManager, times(1)).find(BankAccountJpaEntity.class, 99L);
    }

    @Test
    void findByIban_ShouldReturnBankAccount_WhenIbanExists() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(BankAccountJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("iban"), anyString())).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(accountEntity);

        // Act
        Optional<BankAccountJpaEntity> result = bankAccountJpaDao.findByIban("ES1234567890123456789012");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("ES1234567890123456789012", result.get().getIban());
        verify(typedQuery, times(1)).setParameter("iban", "ES1234567890123456789012");
    }

    @Test
    void findByIban_ShouldReturnEmpty_WhenIbanDoesNotExist() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(BankAccountJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("iban"), anyString())).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenThrow(new NoResultException());

        // Act
        Optional<BankAccountJpaEntity> result = bankAccountJpaDao.findByIban("ES9999999999999999999999");

        // Assert
        assertFalse(result.isPresent());
        verify(typedQuery, times(1)).setParameter("iban", "ES9999999999999999999999");
    }

    @Test
    void findByClientId_ShouldReturnListOfBankAccounts() {
        // Arrange
        BankAccountJpaEntity account2 = new BankAccountJpaEntity();
        account2.setId(2L);
        account2.setIban("ES9876543210987654321098");
        account2.setClient(clientEntity);

        when(entityManager.createQuery(anyString(), eq(BankAccountJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("clientId"), anyLong())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Arrays.asList(accountEntity, account2));

        // Act
        List<BankAccountJpaEntity> result = bankAccountJpaDao.findByClientId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("ES1234567890123456789012", result.get(0).getIban());
        assertEquals("ES9876543210987654321098", result.get(1).getIban());
        verify(typedQuery, times(1)).setParameter("clientId", 1L);
    }

    @Test
    void findByClientId_ShouldReturnEmptyList_WhenClientHasNoAccounts() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(BankAccountJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("clientId"), anyLong())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        // Act
        List<BankAccountJpaEntity> result = bankAccountJpaDao.findByClientId(99L);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void insert_ShouldPersistBankAccount() {
        // Arrange
        BankAccountJpaEntity newAccount = new BankAccountJpaEntity();
        newAccount.setIban("ES1111222233334444555566");
        newAccount.setBalance(new BigDecimal("500.00"));
        doNothing().when(entityManager).persist(any(BankAccountJpaEntity.class));

        // Act
        BankAccountJpaEntity result = bankAccountJpaDao.insert(newAccount);

        // Assert
        assertNotNull(result);
        assertEquals("ES1111222233334444555566", result.getIban());
        verify(entityManager, times(1)).persist(newAccount);
    }

    @Test
    void update_ShouldMergeBankAccount() {
        // Arrange
        accountEntity.setBalance(new BigDecimal("1500.00"));
        when(entityManager.merge(any(BankAccountJpaEntity.class))).thenReturn(accountEntity);

        // Act
        BankAccountJpaEntity result = bankAccountJpaDao.update(accountEntity);

        // Assert
        assertNotNull(result);
        assertEquals(new BigDecimal("1500.00"), result.getBalance());
        verify(entityManager, times(1)).merge(accountEntity);
    }


    @Test
    void count_ShouldReturnNumberOfBankAccounts() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(longTypedQuery);
        when(longTypedQuery.getSingleResult()).thenReturn(10L);

        // Act
        long result = bankAccountJpaDao.count();

        // Assert
        assertEquals(10L, result);
        verify(entityManager, times(1)).createQuery(contains("COUNT(b)"), eq(Long.class));
    }

    @Test
    void count_ShouldReturnZero_WhenNoAccounts() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(longTypedQuery);
        when(longTypedQuery.getSingleResult()).thenReturn(0L);

        // Act
        long result = bankAccountJpaDao.count();

        // Assert
        assertEquals(0L, result);
    }

    @Test
    void findByClientId_ShouldUseCorrectJPQLQuery() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(BankAccountJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("clientId"), anyLong())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        // Act
        bankAccountJpaDao.findByClientId(1L);

        // Assert
        verify(entityManager, times(1)).createQuery(contains("b.client.id = :clientId"), eq(BankAccountJpaEntity.class));
    }

    @Test
    void insert_ShouldReturnSameEntity() {
        // Arrange
        doNothing().when(entityManager).persist(any(BankAccountJpaEntity.class));

        // Act
        BankAccountJpaEntity result = bankAccountJpaDao.insert(accountEntity);

        // Assert
        assertSame(accountEntity, result);
    }
}

