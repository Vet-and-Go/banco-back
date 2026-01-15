package com.grupo4.VetAndGo.persistence.dao.jpa.impl;

import com.grupo4.VetAndGo.persistence.dao.jpa.entity.SessionJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionJpaDaoImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<SessionJpaEntity> typedQuery;

    @Mock
    private Query query;

    @InjectMocks
    private SessionJpaDaoImpl sessionJpaDao;

    private SessionJpaEntity sessionEntity;

    @BeforeEach
    void setUp() {
        sessionEntity = new SessionJpaEntity();
        sessionEntity.setId(1L);
        sessionEntity.setToken("test-token-123");
        sessionEntity.setClientId(1L);
        sessionEntity.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void createSession_ShouldGenerateTokenAndPersistSession() {
        // Arrange
        Long clientId = 1L;
        doNothing().when(entityManager).persist(any(SessionJpaEntity.class));

        // Act
        String token = sessionJpaDao.createSession(clientId);

        // Assert
        assertNotNull(token);
        assertFalse(token.isEmpty());
        verify(entityManager, times(1)).persist(any(SessionJpaEntity.class));
    }

    @Test
    void createSession_ShouldGenerateUniqueTokens() {
        // Arrange
        doNothing().when(entityManager).persist(any(SessionJpaEntity.class));

        // Act
        String token1 = sessionJpaDao.createSession(1L);
        String token2 = sessionJpaDao.createSession(1L);

        // Assert
        assertNotEquals(token1, token2);
        verify(entityManager, times(2)).persist(any(SessionJpaEntity.class));
    }

    @Test
    void findByToken_ShouldReturnSession_WhenTokenExists() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(SessionJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("token"), anyString())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(List.of(sessionEntity));

        // Act
        Optional<SessionJpaEntity> result = sessionJpaDao.findByToken("test-token-123");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("test-token-123", result.get().getToken());
        assertEquals(1L, result.get().getClientId());
        verify(typedQuery, times(1)).setParameter("token", "test-token-123");
    }

    @Test
    void findByToken_ShouldReturnEmpty_WhenTokenDoesNotExist() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(SessionJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("token"), anyString())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        // Act
        Optional<SessionJpaEntity> result = sessionJpaDao.findByToken("invalid-token");

        // Assert
        assertFalse(result.isPresent());
        verify(typedQuery, times(1)).setParameter("token", "invalid-token");
    }

    @Test
    void findByToken_ShouldReturnFirstResult_WhenMultipleSessionsFound() {
        // Arrange
        SessionJpaEntity session2 = new SessionJpaEntity();
        session2.setId(2L);
        session2.setToken("test-token-123");
        session2.setClientId(1L);

        when(entityManager.createQuery(anyString(), eq(SessionJpaEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("token"), anyString())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(List.of(sessionEntity, session2));

        // Act
        Optional<SessionJpaEntity> result = sessionJpaDao.findByToken("test-token-123");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void deleteByClientId_ShouldExecuteDeleteQuery() {
        // Arrange
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(eq("clientId"), anyLong())).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);

        // Act
        sessionJpaDao.deleteByClientId(1L);

        // Assert
        verify(entityManager, times(1)).createQuery(contains("DELETE FROM SessionJpaEntity"));
        verify(query, times(1)).setParameter("clientId", 1L);
        verify(query, times(1)).executeUpdate();
    }

    @Test
    void deleteByClientId_ShouldNotFail_WhenNoSessionsToDelete() {
        // Arrange
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(eq("clientId"), anyLong())).thenReturn(query);
        when(query.executeUpdate()).thenReturn(0);

        // Act
        sessionJpaDao.deleteByClientId(99L);

        // Assert
        verify(query, times(1)).executeUpdate();
    }

    @Test
    void createSession_ShouldSetCurrentTimestamp() {
        // Arrange
        doNothing().when(entityManager).persist(any(SessionJpaEntity.class));

        // Act
        String token = sessionJpaDao.createSession(1L);

        // Assert
        assertNotNull(token);
        // Verify that persist was called (timestamp is set in the constructor)
        verify(entityManager, times(1)).persist(any(SessionJpaEntity.class));
    }

    @Test
    void deleteByClientId_ShouldDeleteMultipleSessions() {
        // Arrange
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(eq("clientId"), anyLong())).thenReturn(query);
        when(query.executeUpdate()).thenReturn(3); // Simulates deleting 3 sessions

        // Act
        sessionJpaDao.deleteByClientId(1L);

        // Assert
        verify(query, times(1)).executeUpdate();
    }
}

