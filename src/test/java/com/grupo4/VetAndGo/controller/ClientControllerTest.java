package com.grupo4.VetAndGo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo4.VetAndGo.domain.model.Client;
import com.grupo4.VetAndGo.domain.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Autowired
    private ObjectMapper objectMapper;

    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client(1L, "user1", "pass1", "John", "Doe", "Smith", "12345678A");
    }

    @Test
    void findAll_ShouldReturnListOfClients() throws Exception {
        when(clientService.findAll()).thenReturn(Arrays.asList(client));

        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].login").value("user1"));
    }

    @Test
    void findById_ShouldReturnClient_WhenClientExists() throws Exception {
        when(clientService.findById(1L)).thenReturn(Optional.of(client));

        mockMvc.perform(get("/api/clients/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value("user1"));
    }

    @Test
    void findById_ShouldReturnNotFound_WhenClientDoesNotExist() throws Exception {
        when(clientService.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/clients/{id}", 99L))
                .andExpect(status().isNotFound());
    }

    @Test
    void login_ShouldReturnToken_WhenCredentialsAreValid() throws Exception {
        when(clientService.login("user1", "pass1")).thenReturn("token123");

        String loginRequest = "{\"username\":\"user1\",\"password\":\"pass1\"}";

        mockMvc.perform(post("/api/clients/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token123"))
                .andExpect(jsonPath("$.username").value("user1"));
    }

    @Test
    void validateSession_ShouldReturnClient_WhenTokenIsValid() throws Exception {
        when(clientService.validateSession("validToken")).thenReturn(Optional.of(client));

        mockMvc.perform(post("/api/clients/auth/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("validToken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value("user1"));
    }

    @Test
    void logout_ShouldReturnNoContent_WhenLogoutSuccessful() throws Exception {
        String logoutRequest = "{\"username\":\"user1\",\"password\":\"pass1\"}";

        mockMvc.perform(post("/api/clients/auth/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(logoutRequest))
                .andExpect(status().isNoContent());
    }
}
