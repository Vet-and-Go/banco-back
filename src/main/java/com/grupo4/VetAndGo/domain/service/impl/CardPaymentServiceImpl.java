package com.grupo4.VetAndGo.domain.service.impl;

import com.grupo4.VetAndGo.domain.exception.BussinesException;
import com.grupo4.VetAndGo.domain.model.BankAccount;
import com.grupo4.VetAndGo.domain.model.BankTransaction;
import com.grupo4.VetAndGo.domain.model.CardPayment;
import com.grupo4.VetAndGo.domain.model.CreditCard;
import com.grupo4.VetAndGo.domain.model.TransactionOrigin;
import com.grupo4.VetAndGo.domain.model.TransactionType;
import com.grupo4.VetAndGo.domain.repository.BankAccountRepository;
import com.grupo4.VetAndGo.domain.repository.CreditCardRepository;
import com.grupo4.VetAndGo.domain.service.BankTransactionService;
import com.grupo4.VetAndGo.domain.service.CardPaymentService;
import java.math.BigDecimal;

public class CardPaymentServiceImpl implements CardPaymentService {

    private final CreditCardRepository creditCardRepository;
    private final BankAccountRepository bankAccountRepository;
    private final BankTransactionService bankTransactionService;

    public CardPaymentServiceImpl(CreditCardRepository creditCardRepository,
            BankAccountRepository bankAccountRepository,
            BankTransactionService bankTransactionService) {
        this.creditCardRepository = creditCardRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.bankTransactionService = bankTransactionService;
    }

    @Override
    public void processPayment(CardPayment payment) {
        String destinationIban = payment.getDestinationIban();
        if (destinationIban == null || !destinationIban.replaceAll("\\s+", "").matches("^ES\\d{22}$")) {
            throw new IllegalArgumentException(
                    "Transaction failed: Invalid IBAN format. Must start with ES and have 22 digits.");
        }

        BigDecimal amount = payment.getAmount();
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transaction failed: Amount must be greater than zero.");
        }

        String concept = payment.getConcept();
        if (concept == null || concept.trim().length() < 3) {
            throw new IllegalArgumentException("Transaction failed: Concept must have at least 3 characters.");
        }

        CreditCard card = creditCardRepository.findByCardNumber(payment.getCardNumber())
                .orElseThrow(() -> new IllegalArgumentException("Credit card authentication failed: Card not found."));

        payment.validateCredentials(card);

        if (card.isExpired()) {
            throw new IllegalArgumentException("Transaction declined: Credit card has expired.");
        }

        BankAccount fromAccount = card.getBankAccount();
        if (fromAccount == null) {
            throw new BussinesException("System error: Credit card is not linked to any active bank account.");
        }

        BankAccount toAccount = bankAccountRepository.findByIban(destinationIban)
                .orElseThrow(() -> new BussinesException(
                        "Transaction failed: Destination account IBAN not found or does not exist."));

        if (fromAccount.getBalance() == null) {
            fromAccount.setBalance(BigDecimal.ZERO);
        }
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Transaction declined: Insufficient funds in the linked bank account.");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        bankAccountRepository.save(fromAccount);

        bankTransactionService.createTransaction(fromAccount, amount, concept, TransactionType.DEBIT,
                TransactionOrigin.CARD, payment.getCardNumber());

        if (toAccount.getBalance() == null) {
            toAccount.setBalance(BigDecimal.ZERO);
        }
        toAccount.setBalance(toAccount.getBalance().add(amount));
        bankAccountRepository.save(toAccount);

        bankTransactionService.createTransaction(toAccount, amount, concept, TransactionType.CREDIT,
                TransactionOrigin.CARD);
    }

}
