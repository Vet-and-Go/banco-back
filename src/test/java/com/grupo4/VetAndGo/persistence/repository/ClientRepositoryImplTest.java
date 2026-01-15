package com.grupo4.VetAndGo.persistence.repository;

import com.grupo4.VetAndGo.domain.model.Client;
import com.grupo4.VetAndGo.persistence.dao.jpa.ClientJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.ClientJpaEntity;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientRepositoryImplTest {

    @Mock
    private ClientJpaDao clientJpaDao;

    @InjectMocks
    private ClientRepositoryImpl clientRepository;

    private ClientJpaEntity clientEntity;
    private Client client;

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

        client = new Client();
        client.setId(1L);
        client.setLogin("user1");
        client.setPassword("hashedPassword");
        client.setFirstName("John");
        client.setLastName("Doe");
        client.setSecondLastName("Smith");
        client.setDni("12345678A");
    }

    @Test
    void findAll_ShouldReturnListOfClients() {
        // Arrange
        when(clientJpaDao.findAll()).thenReturn(Arrays.asList(clientEntity));

        // Act
        List<Client> result = clientRepository.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("user1", result.get(0).getLogin());
        verify(clientJpaDao, times(1)).findAll();
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenNoClients() {
        // Arrange
        when(clientJpaDao.findAll()).thenReturn(Arrays.asList());

        // Act
        List<Client> result = clientRepository.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(clientJpaDao, times(1)).findAll();
    }

    @Test
    void findById_ShouldReturnClient_WhenClientExists() {
        // Arrange
        when(clientJpaDao.findById(1L)).thenReturn(Optional.of(clientEntity));

        // Act
        Optional<Client> result = clientRepository.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("user1", result.get().getLogin());
        assertEquals("John", result.get().getFirstName());
        verify(clientJpaDao, times(1)).findById(1L);
    }

    @Test
    void findById_ShouldReturnEmpty_WhenClientDoesNotExist() {
        // Arrange
        when(clientJpaDao.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<Client> result = clientRepository.findById(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(clientJpaDao, times(1)).findById(99L);
    }

    @Test
    void findByLogin_ShouldReturnClient_WhenClientExists() {
        // Arrange
        when(clientJpaDao.findByLogin("user1")).thenReturn(Optional.of(clientEntity));

        // Act
        Optional<Client> result = clientRepository.findByLogin("user1");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("user1", result.get().getLogin());
        verify(clientJpaDao, times(1)).findByLogin("user1");
    }

    @Test
    void findByLogin_ShouldReturnEmpty_WhenClientDoesNotExist() {
        // Arrange
        when(clientJpaDao.findByLogin("unknown")).thenReturn(Optional.empty());

        // Act
        Optional<Client> result = clientRepository.findByLogin("unknown");

        // Assert
        assertFalse(result.isPresent());
        verify(clientJpaDao, times(1)).findByLogin("unknown");
    }
}

