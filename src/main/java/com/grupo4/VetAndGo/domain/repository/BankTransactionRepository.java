package com.grupo4.VetAndGo.domain.repository;

import com.grupo4.VetAndGo.domain.model.BankTransaction;
import java.util.List;
import java.util.Optional;

public interface BankTransactionRepository {
    List<BankTransaction> findAll();
    Optional<BankTransaction> findById(Long id);
    BankTransaction save(BankTransaction bankTransaction);
    void deleteById(Long id);
}
