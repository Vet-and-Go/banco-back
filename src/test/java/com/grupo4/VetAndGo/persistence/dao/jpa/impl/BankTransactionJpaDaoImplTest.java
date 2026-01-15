package com.grupo4.VetAndGo.persistence.dao.jpa.impl;

import com.grupo4.VetAndGo.domain.model.TransactionOrigin;
import com.grupo4.VetAndGo.domain.model.TransactionType;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.BankAccountJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.BankTransactionJpaEntity;
import jakarta.persistence.EntityManager;
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
class BankTransactionJpaDaoImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<BankTransactionJpaEntity> typedQuery;

    @Mock
    private TypedQuery<Long> longTypedQuery;

    @InjectMocks
    private BankTransactionJpaDaoImpl bankTransactionJpaDao;

    private BankTransactionJpaEntity transactionEntity;
    private BankAccountJpaEntity accountEntity;

    @BeforeEach
    void setUp() {
        accountEntity = new BankAccountJpaEntity();
        accountEntity.setId(1L);
        accountEntity.setIban("ES1234567890123456789012");

        transactionEntity = new BankTransactionJpaEntity();
        transactionEntity.setId(1L);
        transactionEntity.setDate("2026-01-15T10:30:00");
        transactionEntity.setAmount(new BigDecimal("100.00"));
        transactionEntity.setConcept("Test transaction");
        transactionEntity.setType(TransactionType.DEBIT);
        transactionEntity.setOrigin(TransactionOrigin.CARD);
        transactionEntity.setCardNumber("1234567890123456");
        transactionEntity.setBankAccount(accountEntity);
    }

    @Test
    void findAll_ShouldReturnListOfTransactions() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(BankTransactionJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Arrays.asList(transactionEntity));

        // Act
        List<BankTransactionJpaEntity> result = bankTransactionJpaDao.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test transaction", result.get(0).getConcept());
        verify(entityManager, times(1)).createQuery(contains("SELECT t FROM BankTransactionJpaEntity t"), eq(BankTransactionJpaEntity.class));
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenNoTransactions() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(BankTransactionJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        // Act
        List<BankTransactionJpaEntity> result = bankTransactionJpaDao.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByAccountId_ShouldReturnListOfTransactions() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(BankTransactionJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("accountId"), anyLong())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Arrays.asList(transactionEntity));

        // Act
        List<BankTransactionJpaEntity> result = bankTransactionJpaDao.findByAccountId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test transaction", result.get(0).getConcept());
        verify(typedQuery, times(1)).setParameter("accountId", 1L);
    }

    @Test
    void findByAccountId_ShouldReturnEmptyList_WhenNoTransactionsForAccount() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(BankTransactionJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("accountId"), anyLong())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        // Act
        List<BankTransactionJpaEntity> result = bankTransactionJpaDao.findByAccountId(99L);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByAccountIdAndOrigin_ShouldReturnFilteredTransactions() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(BankTransactionJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("accountId"), anyLong())).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("origin"), any(TransactionOrigin.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Arrays.asList(transactionEntity));

        // Act
        List<BankTransactionJpaEntity> result = bankTransactionJpaDao.findByAccountIdAndOrigin(1L, TransactionOrigin.CARD);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(TransactionOrigin.CARD, result.get(0).getOrigin());
        verify(typedQuery, times(1)).setParameter("accountId", 1L);
        verify(typedQuery, times(1)).setParameter("origin", TransactionOrigin.CARD);
    }

    @Test
    void findByAccountIdAndOrigin_ShouldReturnEmptyList_WhenNoMatchingTransactions() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(BankTransactionJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("accountId"), anyLong())).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("origin"), any(TransactionOrigin.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        // Act
        List<BankTransactionJpaEntity> result = bankTransactionJpaDao.findByAccountIdAndOrigin(1L, TransactionOrigin.TRANSFER);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByCardNumber_ShouldReturnListOfTransactions() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(BankTransactionJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("cardNumber"), anyString())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Arrays.asList(transactionEntity));

        // Act
        List<BankTransactionJpaEntity> result = bankTransactionJpaDao.findByCardNumber("1234567890123456");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("1234567890123456", result.get(0).getCardNumber());
        verify(typedQuery, times(1)).setParameter("cardNumber", "1234567890123456");
    }

    @Test
    void findByCardNumber_ShouldReturnEmptyList_WhenNoMatchingTransactions() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(BankTransactionJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("cardNumber"), anyString())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        // Act
        List<BankTransactionJpaEntity> result = bankTransactionJpaDao.findByCardNumber("9999999999999999");

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findById_ShouldReturnTransaction_WhenTransactionExists() {
        // Arrange
        when(entityManager.find(BankTransactionJpaEntity.class, 1L)).thenReturn(transactionEntity);

        // Act
        Optional<BankTransactionJpaEntity> result = bankTransactionJpaDao.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Test transaction", result.get().getConcept());
        verify(entityManager, times(1)).find(BankTransactionJpaEntity.class, 1L);
    }

    @Test
    void findById_ShouldReturnEmpty_WhenTransactionDoesNotExist() {
        // Arrange
        when(entityManager.find(BankTransactionJpaEntity.class, 99L)).thenReturn(null);

        // Act
        Optional<BankTransactionJpaEntity> result = bankTransactionJpaDao.findById(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(entityManager, times(1)).find(BankTransactionJpaEntity.class, 99L);
    }

    @Test
    void insert_ShouldPersistTransaction() {
        // Arrange
        BankTransactionJpaEntity newTransaction = new BankTransactionJpaEntity();
        newTransaction.setAmount(new BigDecimal("50.00"));
        newTransaction.setConcept("New transaction");
        doNothing().when(entityManager).persist(any(BankTransactionJpaEntity.class));

        // Act
        BankTransactionJpaEntity result = bankTransactionJpaDao.insert(newTransaction);

        // Assert
        assertNotNull(result);
        assertEquals("New transaction", result.getConcept());
        verify(entityManager, times(1)).persist(newTransaction);
    }

    @Test
    void update_ShouldMergeTransaction() {
        // Arrange
        transactionEntity.setConcept("Updated concept");
        when(entityManager.merge(any(BankTransactionJpaEntity.class))).thenReturn(transactionEntity);

        // Act
        BankTransactionJpaEntity result = bankTransactionJpaDao.update(transactionEntity);

        // Assert
        assertNotNull(result);
        assertEquals("Updated concept", result.getConcept());
        verify(entityManager, times(1)).merge(transactionEntity);
    }





    @Test
    void count_ShouldReturnNumberOfTransactions() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(longTypedQuery);
        when(longTypedQuery.getSingleResult()).thenReturn(25L);

        // Act
        long result = bankTransactionJpaDao.count();

        // Assert
        assertEquals(25L, result);
        verify(entityManager, times(1)).createQuery(contains("COUNT(t)"), eq(Long.class));
    }

    @Test
    void count_ShouldReturnZero_WhenNoTransactions() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(longTypedQuery);
        when(longTypedQuery.getSingleResult()).thenReturn(0L);

        // Act
        long result = bankTransactionJpaDao.count();

        // Assert
        assertEquals(0L, result);
    }

    @Test
    void findByAccountId_ShouldUseCorrectJPQLQuery() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(BankTransactionJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("accountId"), anyLong())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        // Act
        bankTransactionJpaDao.findByAccountId(1L);

        // Assert
        verify(entityManager, times(1)).createQuery(contains("t.bankAccount.id = :accountId"), eq(BankTransactionJpaEntity.class));
    }

    @Test
    void findByAccountIdAndOrigin_ShouldUseCorrectJPQLQuery() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(BankTransactionJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        // Act
        bankTransactionJpaDao.findByAccountIdAndOrigin(1L, TransactionOrigin.CARD);

        // Assert
        verify(entityManager, times(1)).createQuery(
                contains("t.bankAccount.id = :accountId AND t.origin = :origin"),
                eq(BankTransactionJpaEntity.class)
        );
    }

    @Test
    void insert_ShouldReturnSameEntity() {
        // Arrange
        doNothing().when(entityManager).persist(any(BankTransactionJpaEntity.class));

        // Act
        BankTransactionJpaEntity result = bankTransactionJpaDao.insert(transactionEntity);

        // Assert
        assertSame(transactionEntity, result);
    }
}

