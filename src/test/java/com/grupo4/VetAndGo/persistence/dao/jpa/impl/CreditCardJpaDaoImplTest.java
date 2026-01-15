package com.grupo4.VetAndGo.persistence.dao.jpa.impl;

import com.grupo4.VetAndGo.persistence.dao.jpa.entity.BankAccountJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.ClientJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.CreditCardJpaEntity;
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
class CreditCardJpaDaoImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<CreditCardJpaEntity> typedQuery;

    @Mock
    private TypedQuery<Long> longTypedQuery;

    @InjectMocks
    private CreditCardJpaDaoImpl creditCardJpaDao;

    private CreditCardJpaEntity cardEntity;
    private BankAccountJpaEntity accountEntity;
    private ClientJpaEntity clientEntity;

    @BeforeEach
    void setUp() {
        clientEntity = new ClientJpaEntity();
        clientEntity.setId(1L);
        clientEntity.setFirstName("John");

        accountEntity = new BankAccountJpaEntity();
        accountEntity.setId(1L);
        accountEntity.setIban("ES1234567890123456789012");
        accountEntity.setBalance(new BigDecimal("1000.00"));
        accountEntity.setClient(clientEntity);

        cardEntity = new CreditCardJpaEntity();
        cardEntity.setId(1L);
        cardEntity.setCardNumber("1234567890123456");
        cardEntity.setExpirationDate("2026-12");
        cardEntity.setCvc("123");
        cardEntity.setFullName("John Doe");
        cardEntity.setBankAccount(accountEntity);
    }

    @Test
    void findAll_ShouldReturnListOfCreditCards() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(CreditCardJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Arrays.asList(cardEntity));

        // Act
        List<CreditCardJpaEntity> result = creditCardJpaDao.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("1234567890123456", result.get(0).getCardNumber());
        verify(entityManager, times(1)).createQuery(contains("SELECT c FROM CreditCardJpaEntity c"), eq(CreditCardJpaEntity.class));
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenNoCards() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(CreditCardJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        // Act
        List<CreditCardJpaEntity> result = creditCardJpaDao.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findById_ShouldReturnCreditCard_WhenCardExists() {
        // Arrange
        when(entityManager.find(CreditCardJpaEntity.class, 1L)).thenReturn(cardEntity);

        // Act
        Optional<CreditCardJpaEntity> result = creditCardJpaDao.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("1234567890123456", result.get().getCardNumber());
        verify(entityManager, times(1)).find(CreditCardJpaEntity.class, 1L);
    }

    @Test
    void findById_ShouldReturnEmpty_WhenCardDoesNotExist() {
        // Arrange
        when(entityManager.find(CreditCardJpaEntity.class, 99L)).thenReturn(null);

        // Act
        Optional<CreditCardJpaEntity> result = creditCardJpaDao.findById(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(entityManager, times(1)).find(CreditCardJpaEntity.class, 99L);
    }

    @Test
    void findByCardNumber_ShouldReturnCreditCard_WhenCardNumberExists() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(CreditCardJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("cardNumber"), anyString())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Arrays.asList(cardEntity));

        // Act
        Optional<CreditCardJpaEntity> result = creditCardJpaDao.findByCardNumber("1234567890123456");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("1234567890123456", result.get().getCardNumber());
        verify(typedQuery, times(1)).setParameter("cardNumber", "1234567890123456");
    }

    @Test
    void findByCardNumber_ShouldReturnEmpty_WhenCardNumberDoesNotExist() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(CreditCardJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("cardNumber"), anyString())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        // Act
        Optional<CreditCardJpaEntity> result = creditCardJpaDao.findByCardNumber("9999999999999999");

        // Assert
        assertFalse(result.isPresent());
        verify(typedQuery, times(1)).setParameter("cardNumber", "9999999999999999");
    }

    @Test
    void findByCardNumber_ShouldReturnFirstResult_WhenMultipleCardsFound() {
        // Arrange
        CreditCardJpaEntity card2 = new CreditCardJpaEntity();
        card2.setId(2L);
        card2.setCardNumber("1234567890123456");

        when(entityManager.createQuery(anyString(), eq(CreditCardJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("cardNumber"), anyString())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Arrays.asList(cardEntity, card2));

        // Act
        Optional<CreditCardJpaEntity> result = creditCardJpaDao.findByCardNumber("1234567890123456");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId()); // First result
    }

    @Test
    void findByClientId_ShouldReturnListOfCreditCards() {
        // Arrange
        CreditCardJpaEntity card2 = new CreditCardJpaEntity();
        card2.setId(2L);
        card2.setCardNumber("9876543210987654");
        card2.setBankAccount(accountEntity);

        when(entityManager.createQuery(anyString(), eq(CreditCardJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("clientId"), anyLong())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Arrays.asList(cardEntity, card2));

        // Act
        List<CreditCardJpaEntity> result = creditCardJpaDao.findByClientId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("1234567890123456", result.get(0).getCardNumber());
        assertEquals("9876543210987654", result.get(1).getCardNumber());
        verify(typedQuery, times(1)).setParameter("clientId", 1L);
    }

    @Test
    void findByClientId_ShouldReturnEmptyList_WhenClientHasNoCards() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(CreditCardJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("clientId"), anyLong())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        // Act
        List<CreditCardJpaEntity> result = creditCardJpaDao.findByClientId(99L);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByClientId_ShouldUseCorrectJPQLQueryWithJoin() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(CreditCardJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("clientId"), anyLong())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        // Act
        creditCardJpaDao.findByClientId(1L);

        // Assert
        verify(entityManager, times(1)).createQuery(
                contains("JOIN c.bankAccount b WHERE b.client.id = :clientId"),
                eq(CreditCardJpaEntity.class)
        );
    }

    @Test
    void insert_ShouldPersistCreditCard() {
        // Arrange
        CreditCardJpaEntity newCard = new CreditCardJpaEntity();
        newCard.setCardNumber("5555666677778888");
        newCard.setExpirationDate("2027-03");
        doNothing().when(entityManager).persist(any(CreditCardJpaEntity.class));

        // Act
        CreditCardJpaEntity result = creditCardJpaDao.insert(newCard);

        // Assert
        assertNotNull(result);
        assertEquals("5555666677778888", result.getCardNumber());
        verify(entityManager, times(1)).persist(newCard);
    }

    @Test
    void update_ShouldMergeCreditCard() {
        // Arrange
        cardEntity.setFullName("Jane Doe");
        when(entityManager.merge(any(CreditCardJpaEntity.class))).thenReturn(cardEntity);

        // Act
        CreditCardJpaEntity result = creditCardJpaDao.update(cardEntity);

        // Assert
        assertNotNull(result);
        assertEquals("Jane Doe", result.getFullName());
        verify(entityManager, times(1)).merge(cardEntity);
    }



    @Test
    void count_ShouldReturnNumberOfCreditCards() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(longTypedQuery);
        when(longTypedQuery.getSingleResult()).thenReturn(15L);

        // Act
        long result = creditCardJpaDao.count();

        // Assert
        assertEquals(15L, result);
        verify(entityManager, times(1)).createQuery(contains("COUNT(c)"), eq(Long.class));
    }

    @Test
    void count_ShouldReturnZero_WhenNoCards() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(longTypedQuery);
        when(longTypedQuery.getSingleResult()).thenReturn(0L);

        // Act
        long result = creditCardJpaDao.count();

        // Assert
        assertEquals(0L, result);
    }

    @Test
    void insert_ShouldReturnSameEntity() {
        // Arrange
        doNothing().when(entityManager).persist(any(CreditCardJpaEntity.class));

        // Act
        CreditCardJpaEntity result = creditCardJpaDao.insert(cardEntity);

        // Assert
        assertSame(cardEntity, result);
    }

    @Test
    void findByCardNumber_ShouldUseCorrectJPQLQuery() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(CreditCardJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("cardNumber"), anyString())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        // Act
        creditCardJpaDao.findByCardNumber("1234567890123456");

        // Assert
        verify(entityManager, times(1)).createQuery(contains("c.cardNumber = :cardNumber"), eq(CreditCardJpaEntity.class));
    }
}

