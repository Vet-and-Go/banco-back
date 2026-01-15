package com.grupo4.VetAndGo.domain.repository;

import com.grupo4.VetAndGo.domain.model.BankAccount;
import java.util.List;
import java.util.Optional;

public interface BankAccountRepository {
    List<BankAccount> findAll();
    Optional<BankAccount> findById(Long id);
    Optional<BankAccount> findByIban(String iban);
    List<BankAccount> findByClientId(Long clientId);
    BankAccount save(BankAccount bankAccount);
}
