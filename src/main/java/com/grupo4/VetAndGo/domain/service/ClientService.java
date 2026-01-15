package com.grupo4.VetAndGo.domain.service;


import com.grupo4.VetAndGo.domain.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    public List<Client> findAll();
    Optional<Client> findById(Long id);
    String login(String username, String password);
    void logout(String username);
    Optional<Client> validateSession(String token);
}

