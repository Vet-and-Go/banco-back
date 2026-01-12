package com.grupo4.VetAndGo.domain.mapper;

import com.grupo4.VetAndGo.domain.model.BankAccount;
import com.grupo4.VetAndGo.domain.model.Client;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.BankAccountJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.ClientJpaEntity;

public class BankAccountMapper {

  public static BankAccount toDomain(BankAccountJpaEntity entity) {
    if (entity == null)
      return null;
    return new BankAccount(
        entity.getId(),
        entity.getBalance(),
        entity.getIban(),
        toDomainClient(entity.getClient()));
  }

  public static BankAccountJpaEntity toEntity(BankAccount domain) {
    if (domain == null)
      return null;
    BankAccountJpaEntity entity = new BankAccountJpaEntity();
    entity.setId(domain.getId());
    entity.setBalance(domain.getBalance());
    entity.setIban(domain.getIban());
    entity.setClient(toEntityClient(domain.getClient()));
    return entity;
  }

  private static Client toDomainClient(ClientJpaEntity entity) {
    if (entity == null)
      return null;
    Client client = new Client();
    client.setId(entity.getId());
    client.setFirstName(entity.getFirstName());
    return client;
  }

  private static ClientJpaEntity toEntityClient(Client domain) {
    if (domain == null)
      return null;
    ClientJpaEntity entity = new ClientJpaEntity();
    entity.setId(domain.getId());
    return entity;
  }
}
