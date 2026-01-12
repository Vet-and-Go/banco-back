package com.grupo4.VetAndGo.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.grupo4.VetAndGo.domain.mapper.ClientMapper;
import com.grupo4.VetAndGo.domain.model.Client;
import com.grupo4.VetAndGo.domain.repository.ClientRepository;
import com.grupo4.VetAndGo.persistence.dao.jpa.ClientJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.ClientJpaEntity;

import jakarta.transaction.Transactional;

@Transactional
public class ClientRepositoryImpl implements ClientRepository {

  private final ClientJpaDao clientJpaDao;

  public ClientRepositoryImpl(ClientJpaDao clientJpaDao) {
    this.clientJpaDao = clientJpaDao;
  }

  @Override
  public List<Client> findAll() {
    return clientJpaDao.findAll().stream()
        .map(ClientMapper::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<Client> findById(Long id) {
    return clientJpaDao.findById(id).map(ClientMapper::toDomain);
  }

  @Override
  public Client save(Client client) {
    ClientJpaEntity entity = ClientMapper.toEntity(client);
    if (client.getId() == null) {
      return ClientMapper.toDomain(clientJpaDao.insert(entity));
    } else {
      return ClientMapper.toDomain(clientJpaDao.update(entity));
    }
  }

  @Override
  public void deleteById(Long id) {
    clientJpaDao.deleteById(id);
  }
}
