package com.grupo4.VetAndGo.domain.repository;

import com.grupo4.VetAndGo.domain.model.BankTransaction;
import com.grupo4.VetAndGo.domain.model.TransactionOrigin;
import java.util.List;
import java.util.Optional;

public interface BankTransactionRepository {
    List<BankTransaction> findAll();
    List<BankTransaction> findByAccountId(Long accountId);
    List<BankTransaction> findByAccountIdAndOrigin(Long accountId, TransactionOrigin origin);
    List<BankTransaction> findByCardNumber(String cardNumber);
    Optional<BankTransaction> findById(Long id);
    BankTransaction save(BankTransaction bankTransaction);
}
