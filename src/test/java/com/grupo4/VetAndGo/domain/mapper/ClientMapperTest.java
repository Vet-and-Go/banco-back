package com.grupo4.VetAndGo.domain.mapper;

import com.grupo4.VetAndGo.domain.model.Client;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.ClientJpaEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientMapperTest {

    @Test
    void toDomainClient_ShouldMapEntityToModel_WhenValidEntityProvided() {
        // Arrange
        ClientJpaEntity entity = new ClientJpaEntity();
        entity.setId(1L);
        entity.setLogin("user1");
        entity.setPassword("pass123");
        entity.setFirstName("John");
        entity.setLastName("Doe");
        entity.setSecondLastName("Smith");
        entity.setDni("12345678A");

        // Act
        Client result = ClientMapper.toDomainClient(entity);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("user1", result.getLogin());
        assertEquals("pass123", result.getPassword());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("Smith", result.getSecondLastName());
        assertEquals("12345678A", result.getDni());
    }

    @Test
    void toDomainClient_ShouldReturnNull_WhenEntityIsNull() {
        // Act
        Client result = ClientMapper.toDomainClient(null);

        // Assert
        assertNull(result);
    }

    @Test
    void fromDomainClienttojpaEntity_ShouldMapModelToEntity_WhenValidModelProvided() {
        // Arrange
        Client domain = new Client(1L, "user1", "pass123", "John", "Doe", "Smith", "12345678A");

        // Act
        ClientJpaEntity result = ClientMapper.fromDomainClienttojpaEntity(domain);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("user1", result.getLogin());
        assertEquals("pass123", result.getPassword());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("Smith", result.getSecondLastName());
        assertEquals("12345678A", result.getDni());
    }

    @Test
    void fromDomainClienttojpaEntity_ShouldReturnNull_WhenModelIsNull() {
        // Act
        ClientJpaEntity result = ClientMapper.fromDomainClienttojpaEntity(null);

        // Assert
        assertNull(result);
    }

    @Test
    void toDomainClient_ShouldHandleNullFields_WhenEntityHasNullFields() {
        // Arrange
        ClientJpaEntity entity = new ClientJpaEntity();
        entity.setId(1L);
        entity.setLogin("user1");
        entity.setPassword(null);
        entity.setFirstName(null);
        entity.setLastName(null);
        entity.setSecondLastName(null);
        entity.setDni(null);

        // Act
        Client result = ClientMapper.toDomainClient(entity);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("user1", result.getLogin());
        assertNull(result.getPassword());
        assertNull(result.getFirstName());
    }
}

