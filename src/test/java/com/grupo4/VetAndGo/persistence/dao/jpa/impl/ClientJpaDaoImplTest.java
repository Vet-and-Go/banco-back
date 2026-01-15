package com.grupo4.VetAndGo.persistence.dao.jpa.impl;

import com.grupo4.VetAndGo.persistence.dao.jpa.entity.ClientJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientJpaDaoImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<ClientJpaEntity> typedQuery;

    @Mock
    private TypedQuery<Long> longTypedQuery;

    @InjectMocks
    private ClientJpaDaoImpl clientJpaDao;

    private ClientJpaEntity clientEntity;

    @BeforeEach
    void setUp() {
        clientEntity = new ClientJpaEntity();
        clientEntity.setId(1L);
        clientEntity.setLogin("user1");
        clientEntity.setPassword("hashedPassword");
        clientEntity.setFirstName("John");
        clientEntity.setLastName("Doe");
        clientEntity.setSecondLastName("Smith");
        clientEntity.setDni("12345678A");
    }

    @Test
    void findAll_ShouldReturnListOfClients() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(ClientJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Arrays.asList(clientEntity));

        // Act
        List<ClientJpaEntity> result = clientJpaDao.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("user1", result.get(0).getLogin());
        verify(entityManager, times(1)).createQuery(contains("SELECT c FROM ClientJpaEntity c"), eq(ClientJpaEntity.class));
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenNoClients() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(ClientJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        // Act
        List<ClientJpaEntity> result = clientJpaDao.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findById_ShouldReturnClient_WhenClientExists() {
        // Arrange
        when(entityManager.find(ClientJpaEntity.class, 1L)).thenReturn(clientEntity);

        // Act
        Optional<ClientJpaEntity> result = clientJpaDao.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("user1", result.get().getLogin());
        verify(entityManager, times(1)).find(ClientJpaEntity.class, 1L);
    }

    @Test
    void findById_ShouldReturnEmpty_WhenClientDoesNotExist() {
        // Arrange
        when(entityManager.find(ClientJpaEntity.class, 99L)).thenReturn(null);

        // Act
        Optional<ClientJpaEntity> result = clientJpaDao.findById(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(entityManager, times(1)).find(ClientJpaEntity.class, 99L);
    }

    @Test
    void findByLogin_ShouldReturnClient_WhenLoginExists() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(ClientJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("login"), anyString())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Arrays.asList(clientEntity));

        // Act
        Optional<ClientJpaEntity> result = clientJpaDao.findByLogin("user1");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("user1", result.get().getLogin());
        verify(typedQuery, times(1)).setParameter("login", "user1");
    }

    @Test
    void findByLogin_ShouldReturnEmpty_WhenLoginDoesNotExist() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(ClientJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("login"), anyString())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        // Act
        Optional<ClientJpaEntity> result = clientJpaDao.findByLogin("unknown");

        // Assert
        assertFalse(result.isPresent());
        verify(typedQuery, times(1)).setParameter("login", "unknown");
    }

    @Test
    void findByLogin_ShouldReturnFirstResult_WhenMultipleClientsFound() {
        // Arrange
        ClientJpaEntity client2 = new ClientJpaEntity();
        client2.setId(2L);
        client2.setLogin("user1");

        when(entityManager.createQuery(anyString(), eq(ClientJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("login"), anyString())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Arrays.asList(clientEntity, client2));

        // Act
        Optional<ClientJpaEntity> result = clientJpaDao.findByLogin("user1");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId()); // First result
    }

    @Test
    void insert_ShouldPersistClient() {
        // Arrange
        ClientJpaEntity newClient = new ClientJpaEntity();
        newClient.setLogin("newuser");
        newClient.setPassword("password");
        doNothing().when(entityManager).persist(any(ClientJpaEntity.class));

        // Act
        ClientJpaEntity result = clientJpaDao.insert(newClient);

        // Assert
        assertNotNull(result);
        assertEquals("newuser", result.getLogin());
        verify(entityManager, times(1)).persist(newClient);
    }

    @Test
    void update_ShouldMergeClient() {
        // Arrange
        clientEntity.setFirstName("UpdatedName");
        when(entityManager.merge(any(ClientJpaEntity.class))).thenReturn(clientEntity);

        // Act
        ClientJpaEntity result = clientJpaDao.update(clientEntity);

        // Assert
        assertNotNull(result);
        assertEquals("UpdatedName", result.getFirstName());
        verify(entityManager, times(1)).merge(clientEntity);
    }



    @Test
    void count_ShouldReturnNumberOfClients() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(longTypedQuery);
        when(longTypedQuery.getSingleResult()).thenReturn(5L);

        // Act
        long result = clientJpaDao.count();

        // Assert
        assertEquals(5L, result);
        verify(entityManager, times(1)).createQuery(contains("COUNT(c)"), eq(Long.class));
    }

    @Test
    void count_ShouldReturnZero_WhenNoClients() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(longTypedQuery);
        when(longTypedQuery.getSingleResult()).thenReturn(0L);

        // Act
        long result = clientJpaDao.count();

        // Assert
        assertEquals(0L, result);
    }

    @Test
    void insert_ShouldReturnSameEntity() {
        // Arrange
        doNothing().when(entityManager).persist(any(ClientJpaEntity.class));

        // Act
        ClientJpaEntity result = clientJpaDao.insert(clientEntity);

        // Assert
        assertSame(clientEntity, result);
    }
}

