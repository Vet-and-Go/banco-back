package com.grupo4.VetAndGo.domain.service.impl;

import com.grupo4.VetAndGo.domain.exception.ValidationException;
import com.grupo4.VetAndGo.domain.model.Client;
import com.grupo4.VetAndGo.domain.repository.ClientRepository;
import com.grupo4.VetAndGo.domain.service.PasswordEncoderService;
import com.grupo4.VetAndGo.persistence.dao.jpa.SessionJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.SessionJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PasswordEncoderService passwordEncoderService;

    @Mock
    private SessionJpaDao sessionJpaDao;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client(1L, "user1", "hashedPassword", "John", "Doe", "Smith", "12345678A");
    }

    @Test
    void findAll_ShouldReturnListOfClients() {
        // Arrange
        List<Client> clients = Arrays.asList(client);
        when(clientRepository.findAll()).thenReturn(clients);

        // Act
        List<Client> result = clientService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("user1", result.get(0).getLogin());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void findById_ShouldReturnClient_WhenClientExists() {
        // Arrange
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        // Act
        Optional<Client> result = clientService.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("user1", result.get().getLogin());
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void findById_ShouldReturnEmpty_WhenClientDoesNotExist() {
        // Arrange
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<Client> result = clientService.findById(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(clientRepository, times(1)).findById(99L);
    }

    @Test
    void login_ShouldReturnToken_WhenCredentialsAreValid() {
        // Arrange
        when(clientRepository.findByLogin("user1")).thenReturn(Optional.of(client));
        when(passwordEncoderService.verify("password123", "hashedPassword")).thenReturn(true);
        when(sessionJpaDao.createSession(1L)).thenReturn("newToken123");

        // Act
        String token = clientService.login("user1", "password123");

        // Assert
        assertNotNull(token);
        assertEquals("newToken123", token);
        verify(clientRepository, times(1)).findByLogin("user1");
        verify(passwordEncoderService, times(1)).verify("password123", "hashedPassword");
        verify(sessionJpaDao, times(1)).createSession(1L);
    }

    @Test
    void login_ShouldThrowException_WhenClientNotFound() {
        // Arrange
        when(clientRepository.findByLogin("unknown")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ValidationException.class, () -> clientService.login("unknown", "password"));
        verify(clientRepository, times(1)).findByLogin("unknown");
        verify(passwordEncoderService, never()).verify(anyString(), anyString());
        verify(sessionJpaDao, never()).createSession(anyLong());
    }

    @Test
    void login_ShouldThrowException_WhenPasswordIsIncorrect() {
        // Arrange
        when(clientRepository.findByLogin("user1")).thenReturn(Optional.of(client));
        when(passwordEncoderService.verify("wrongPassword", "hashedPassword")).thenReturn(false);

        // Act & Assert
        assertThrows(ValidationException.class, () -> clientService.login("user1", "wrongPassword"));
        verify(clientRepository, times(1)).findByLogin("user1");
        verify(passwordEncoderService, times(1)).verify("wrongPassword", "hashedPassword");
        verify(sessionJpaDao, never()).createSession(anyLong());
    }

    @Test
    void logout_ShouldDeleteSession_WhenClientExists() {
        // Arrange
        when(clientRepository.findByLogin("user1")).thenReturn(Optional.of(client));
        doNothing().when(sessionJpaDao).deleteByClientId(1L);

        // Act
        clientService.logout("user1");

        // Assert
        verify(clientRepository, times(1)).findByLogin("user1");
        verify(sessionJpaDao, times(1)).deleteByClientId(1L);
    }

    @Test
    void logout_ShouldNotDeleteSession_WhenClientDoesNotExist() {
        // Arrange
        when(clientRepository.findByLogin("unknown")).thenReturn(Optional.empty());

        // Act
        clientService.logout("unknown");

        // Assert
        verify(clientRepository, times(1)).findByLogin("unknown");
        verify(sessionJpaDao, never()).deleteByClientId(anyLong());
    }

    @Test
    void validateSession_ShouldReturnClient_WhenTokenIsValid() {
        // Arrange
        SessionJpaEntity session = new SessionJpaEntity();
        session.setClientId(1L);
        session.setToken("validToken");

        when(sessionJpaDao.findByToken("validToken")).thenReturn(Optional.of(session));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        // Act
        Optional<Client> result = clientService.validateSession("validToken");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("user1", result.get().getLogin());
        verify(sessionJpaDao, times(1)).findByToken("validToken");
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void validateSession_ShouldReturnEmpty_WhenTokenIsInvalid() {
        // Arrange
        when(sessionJpaDao.findByToken("invalidToken")).thenReturn(Optional.empty());

        // Act
        Optional<Client> result = clientService.validateSession("invalidToken");

        // Assert
        assertFalse(result.isPresent());
        verify(sessionJpaDao, times(1)).findByToken("invalidToken");
        verify(clientRepository, never()).findById(anyLong());
    }
}

