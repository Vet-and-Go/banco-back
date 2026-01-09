package com.grupo4.VetAndGo.domain.repository;

import com.grupo4.VetAndGo.domain.model.Client;
import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    List<Client> findAll();
    Optional<Client> findById(Long id);
    Client save(Client client);
    void deleteById(Long id);
}
