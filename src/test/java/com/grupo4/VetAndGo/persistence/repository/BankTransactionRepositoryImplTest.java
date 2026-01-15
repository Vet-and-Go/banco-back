package com.grupo4.VetAndGo.persistence.repository;

import com.grupo4.VetAndGo.domain.model.BankTransaction;
import com.grupo4.VetAndGo.domain.model.TransactionOrigin;
import com.grupo4.VetAndGo.domain.model.TransactionType;
import com.grupo4.VetAndGo.persistence.dao.jpa.BankTransactionJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.BankAccountJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.BankTransactionJpaEntity;
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
class BankTransactionRepositoryImplTest {

    @Mock
    private BankTransactionJpaDao bankTransactionJpaDao;

    @InjectMocks
    private BankTransactionRepositoryImpl bankTransactionRepository;

    private BankTransactionJpaEntity transactionEntity;
    private BankTransaction transaction;
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

        transaction = new BankTransaction();
        transaction.setId(1L);
        transaction.setDate("2026-01-15T10:30:00");
        transaction.setAmount(new BigDecimal("100.00"));
        transaction.setConcept("Test transaction");
        transaction.setType(TransactionType.DEBIT);
        transaction.setOrigin(TransactionOrigin.CARD);
        transaction.setCardNumber("1234567890123456");
    }

    @Test
    void findAll_ShouldReturnListOfTransactions() {
        // Arrange
        when(bankTransactionJpaDao.findAll()).thenReturn(Arrays.asList(transactionEntity));

        // Act
        List<BankTransaction> result = bankTransactionRepository.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test transaction", result.get(0).getConcept());
        verify(bankTransactionJpaDao, times(1)).findAll();
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenNoTransactions() {
        // Arrange
        when(bankTransactionJpaDao.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<BankTransaction> result = bankTransactionRepository.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(bankTransactionJpaDao, times(1)).findAll();
    }

    @Test
    void findByAccountId_ShouldReturnListOfTransactions() {
        // Arrange
        when(bankTransactionJpaDao.findByAccountId(1L)).thenReturn(Arrays.asList(transactionEntity));

        // Act
        List<BankTransaction> result = bankTransactionRepository.findByAccountId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test transaction", result.get(0).getConcept());
        verify(bankTransactionJpaDao, times(1)).findByAccountId(1L);
    }

    @Test
    void findByAccountId_ShouldReturnEmptyList_WhenNoTransactionsForAccount() {
        // Arrange
        when(bankTransactionJpaDao.findByAccountId(99L)).thenReturn(Collections.emptyList());

        // Act
        List<BankTransaction> result = bankTransactionRepository.findByAccountId(99L);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(bankTransactionJpaDao, times(1)).findByAccountId(99L);
    }

    @Test
    void findByAccountIdAndOrigin_ShouldReturnFilteredTransactions() {
        // Arrange
        when(bankTransactionJpaDao.findByAccountIdAndOrigin(1L, TransactionOrigin.CARD))
                .thenReturn(Arrays.asList(transactionEntity));

        // Act
        List<BankTransaction> result = bankTransactionRepository.findByAccountIdAndOrigin(1L, TransactionOrigin.CARD);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(TransactionOrigin.CARD, result.get(0).getOrigin());
        verify(bankTransactionJpaDao, times(1)).findByAccountIdAndOrigin(1L, TransactionOrigin.CARD);
    }

    @Test
    void findByAccountIdAndOrigin_ShouldReturnEmptyList_WhenNoMatchingTransactions() {
        // Arrange
        when(bankTransactionJpaDao.findByAccountIdAndOrigin(1L, TransactionOrigin.TRANSFER))
                .thenReturn(Collections.emptyList());

        // Act
        List<BankTransaction> result = bankTransactionRepository.findByAccountIdAndOrigin(1L, TransactionOrigin.TRANSFER);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(bankTransactionJpaDao, times(1)).findByAccountIdAndOrigin(1L, TransactionOrigin.TRANSFER);
    }

    @Test
    void findByCardNumber_ShouldReturnListOfTransactions() {
        // Arrange
        when(bankTransactionJpaDao.findByCardNumber("1234567890123456"))
                .thenReturn(Arrays.asList(transactionEntity));

        // Act
        List<BankTransaction> result = bankTransactionRepository.findByCardNumber("1234567890123456");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("1234567890123456", result.get(0).getCardNumber());
        verify(bankTransactionJpaDao, times(1)).findByCardNumber("1234567890123456");
    }

    @Test
    void findById_ShouldReturnTransaction_WhenTransactionExists() {
        // Arrange
        when(bankTransactionJpaDao.findById(1L)).thenReturn(Optional.of(transactionEntity));

        // Act
        Optional<BankTransaction> result = bankTransactionRepository.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Test transaction", result.get().getConcept());
        verify(bankTransactionJpaDao, times(1)).findById(1L);
    }

    @Test
    void findById_ShouldReturnEmpty_WhenTransactionDoesNotExist() {
        // Arrange
        when(bankTransactionJpaDao.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<BankTransaction> result = bankTransactionRepository.findById(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(bankTransactionJpaDao, times(1)).findById(99L);
    }

    @Test
    void save_ShouldInsertNewTransaction_WhenTransactionIdIsNull() {
        // Arrange
        BankTransaction newTransaction = new BankTransaction();
        newTransaction.setDate("2026-01-16T08:00:00");
        newTransaction.setAmount(new BigDecimal("50.00"));
        newTransaction.setConcept("New transaction");
        newTransaction.setType(TransactionType.CREDIT);
        newTransaction.setOrigin(TransactionOrigin.TRANSFER);

        BankTransactionJpaEntity savedEntity = new BankTransactionJpaEntity();
        savedEntity.setId(2L);
        savedEntity.setDate("2026-01-16T08:00:00");
        savedEntity.setAmount(new BigDecimal("50.00"));
        savedEntity.setConcept("New transaction");

        when(bankTransactionJpaDao.insert(any(BankTransactionJpaEntity.class))).thenReturn(savedEntity);

        // Act
        BankTransaction result = bankTransactionRepository.save(newTransaction);

        // Assert
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("New transaction", result.getConcept());
        verify(bankTransactionJpaDao, times(1)).insert(any(BankTransactionJpaEntity.class));
        verify(bankTransactionJpaDao, never()).update(any(BankTransactionJpaEntity.class));
    }

    @Test
    void save_ShouldUpdateExistingTransaction_WhenTransactionIdIsNotNull() {
        // Arrange
        when(bankTransactionJpaDao.update(any(BankTransactionJpaEntity.class))).thenReturn(transactionEntity);

        // Act
        BankTransaction result = bankTransactionRepository.save(transaction);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test transaction", result.getConcept());
        verify(bankTransactionJpaDao, times(1)).update(any(BankTransactionJpaEntity.class));
        verify(bankTransactionJpaDao, never()).insert(any(BankTransactionJpaEntity.class));
    }



    @Test
    void findByAccountIdAndOrigin_ShouldHandleMultipleTransactions() {
        // Arrange
        BankTransactionJpaEntity transaction2 = new BankTransactionJpaEntity();
        transaction2.setId(2L);
        transaction2.setAmount(new BigDecimal("200.00"));
        transaction2.setOrigin(TransactionOrigin.CARD);

        when(bankTransactionJpaDao.findByAccountIdAndOrigin(1L, TransactionOrigin.CARD))
                .thenReturn(Arrays.asList(transactionEntity, transaction2));

        // Act
        List<BankTransaction> result = bankTransactionRepository.findByAccountIdAndOrigin(1L, TransactionOrigin.CARD);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(t -> t.getOrigin() == TransactionOrigin.CARD));
    }
}

