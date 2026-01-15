package com.grupo4.VetAndGo.domain.service.impl;

import com.grupo4.VetAndGo.domain.exception.ValidationException;
import com.grupo4.VetAndGo.domain.model.Client;
import com.grupo4.VetAndGo.domain.repository.ClientRepository;
import com.grupo4.VetAndGo.domain.service.ClientService;
import com.grupo4.VetAndGo.domain.service.PasswordEncoderService;
import com.grupo4.VetAndGo.persistence.dao.jpa.SessionJpaDao;

import java.util.List;
import java.util.Optional;

public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoderService passwordEncoderService;
    private final SessionJpaDao sessionJpaDao;

    public ClientServiceImpl(ClientRepository clientRepository,
            PasswordEncoderService passwordEncoderService,
            SessionJpaDao sessionJpaDao) {
        this.clientRepository = clientRepository;
        this.passwordEncoderService = passwordEncoderService;
        this.sessionJpaDao = sessionJpaDao;
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public String login(String username, String password) {
        System.out.println("Attempting login for user: " + username);
        Client client = clientRepository.findByLogin(username)
                .orElseThrow(() -> {
                    System.out.println("User not found: " + username);
                    return new ValidationException("Client not found");
                });

        System.out.println("User found. Verifying password...");
        if (!passwordEncoderService.verify(password, client.getPassword())) {
            System.out.println("Password verification failed for user: " + username);
            throw new ValidationException("Incorrect password");
        }

        System.out.println("Password verified. Creating dynamic session...");
        return sessionJpaDao.createSession(client.getId());
    }

    @Override
    public void logout(String username) {
        System.out.println("Logging out client: " + username);
        clientRepository.findByLogin(username).ifPresent(client -> sessionJpaDao.deleteByClientId(client.getId()));
    }

    @Override
    public Optional<Client> validateSession(String token) {
        return sessionJpaDao.findByToken(token)
                .flatMap(session -> clientRepository.findById(session.getClientId()));
    }

}
