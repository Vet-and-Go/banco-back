package com.grupo4.VetAndGo.domain.service.impl;

import com.grupo4.VetAndGo.domain.model.*;
import com.grupo4.VetAndGo.domain.repository.BankAccountRepository;


import com.grupo4.VetAndGo.domain.service.BankAccountService;
import com.grupo4.VetAndGo.domain.exception.*;
 


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;



public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public List<BankAccount> findAll() {
        return bankAccountRepository.findAll();
    }

    @Override
    public Optional<BankAccount> findById(Long id) {
        return bankAccountRepository.findById(id);
    }

    @Override
    public List<BankAccount> findByClientId(Long clientId) {
        return bankAccountRepository.findByClientId(clientId);
    }




}
