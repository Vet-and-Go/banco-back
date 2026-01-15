package com.grupo4.VetAndGo.domain.service;

import com.grupo4.VetAndGo.domain.model.BankAccount;
import com.grupo4.VetAndGo.domain.model.CardPayment;


import java.util.List;
import java.util.Optional;

public interface BankAccountService {
    List<BankAccount> findAll();
    Optional<BankAccount> findById(Long id);
    List<BankAccount> findByClientId(Long clientId);
}
