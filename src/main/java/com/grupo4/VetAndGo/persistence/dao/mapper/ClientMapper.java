package com.grupo4.VetAndGo.persistence.dao.mapper;

import com.grupo4.VetAndGo.domain.model.Client;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.ClientJpaEntity;

public class ClientMapper {

    public static Client toDomainClient(ClientJpaEntity entity) {
        if (entity == null) return null;
        return new Client(
                entity.getId(),
                entity.getLogin(),
                entity.getPassword(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getSecondLastName(),
                entity.getDni()
        );
    }

    public static ClientJpaEntity fromDomainClienttojpaEntity(Client domain) {
        if (domain == null) return null;
        ClientJpaEntity entity = new ClientJpaEntity();
        entity.setId(domain.getId());
        entity.setLogin(domain.getLogin());
        entity.setPassword(domain.getPassword());
        entity.setFirstName(domain.getFirstName());
        entity.setLastName(domain.getLastName());
        entity.setSecondLastName(domain.getSecondLastName());
        entity.setDni(domain.getDni());
        return entity;
    }
}
