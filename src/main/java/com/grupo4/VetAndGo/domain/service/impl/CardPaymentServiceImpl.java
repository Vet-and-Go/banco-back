package com.grupo4.VetAndGo.domain.service.impl;

import com.grupo4.VetAndGo.domain.exception.BussinesException;
import com.grupo4.VetAndGo.domain.model.BankAccount;
import com.grupo4.VetAndGo.domain.model.BankTransaction;
import com.grupo4.VetAndGo.domain.model.CardPayment;
import com.grupo4.VetAndGo.domain.model.CreditCard;
import com.grupo4.VetAndGo.domain.model.TransactionOrigin;
import com.grupo4.VetAndGo.domain.model.TransactionType;
import com.grupo4.VetAndGo.domain.repository.BankAccountRepository;
import com.grupo4.VetAndGo.domain.repository.BankTransactionRepository;
import com.grupo4.VetAndGo.domain.repository.CreditCardRepository;
import com.grupo4.VetAndGo.domain.service.CardPaymentService;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CardPaymentServiceImpl implements CardPaymentService {

    private final CreditCardRepository creditCardRepository;
    private final BankAccountRepository bankAccountRepository;
    private final BankTransactionRepository bankTransactionRepository;

    public CardPaymentServiceImpl(CreditCardRepository creditCardRepository,
                                  BankAccountRepository bankAccountRepository,
                                  BankTransactionRepository bankTransactionRepository) {
        this.creditCardRepository = creditCardRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.bankTransactionRepository = bankTransactionRepository;
    }

    @Override
    public void processPayment(CardPayment payment) {
        CreditCard card = creditCardRepository.findByCardNumber(payment.getCardNumber())
                .orElseThrow(() -> new IllegalArgumentException("Credit card authentication failed: Card not found."));

        if (!card.getCvc().equals(payment.getCardCvv())) {
            throw new IllegalArgumentException("Credit card authentication failed: Invalid CVC.");
        }

        String dtoExpiryStr = payment.getCardExpirationDate();
        if (dtoExpiryStr == null || dtoExpiryStr.isEmpty()) {
            throw new IllegalArgumentException("Payment validation failed: Expiration Date is required.");
        }

        int dtoMonth, dtoYear;
        // Parse dtoExpiryStr manually
        if (dtoExpiryStr.matches("^\\d{4}-\\d{2}$")) {
             dtoYear = Integer.parseInt(dtoExpiryStr.substring(0, 4));
             dtoMonth = Integer.parseInt(dtoExpiryStr.substring(5, 7));
        } else if (dtoExpiryStr.matches("^\\d{2}/\\d{2}$")) {
             dtoMonth = Integer.parseInt(dtoExpiryStr.substring(0, 2));
             dtoYear = 2000 + Integer.parseInt(dtoExpiryStr.substring(3, 5));
        } else if (dtoExpiryStr.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
             dtoYear = Integer.parseInt(dtoExpiryStr.substring(0, 4));
             dtoMonth = Integer.parseInt(dtoExpiryStr.substring(5, 7));
        } else {
             throw new IllegalArgumentException("Payment validation failed: Invalid expiration date format. Expected MM/YY or YYYY-MM.");
        }

        // Check if card matches (Year and Month)
        String cardExpiryStr = card.getExpirationDate(); // Assuming YYYY-MM-DD from DB entity (now String)
        if (cardExpiryStr == null) {
            throw new IllegalArgumentException("System error: Stored card has no expiration date.");
        }
        
        int cardYear, cardMonth;
        // Handle various DB formats potential (YYYY-MM-DD or YYYY-MM)
        if (cardExpiryStr.length() >= 7) {
            // Assume YYYY-MM...
            cardYear = Integer.parseInt(cardExpiryStr.substring(0, 4));
            cardMonth = Integer.parseInt(cardExpiryStr.substring(5, 7));
        } else {
             throw new IllegalArgumentException("System error: Invalid stored card date format.");
        }
        
        if (cardYear != dtoYear || cardMonth != dtoMonth) {
            throw new IllegalArgumentException("Credit card authentication failed: Incorrect expiration date.");
        }

        // Check if expired
        // We still need "now" comparison. Using LocalDate.now() only to get current year/month integers.
        java.time.LocalDate now = java.time.LocalDate.now();
        int currentYear = now.getYear();
        int currentMonth = now.getMonthValue();
        
        if (cardYear < currentYear || (cardYear == currentYear && cardMonth < currentMonth)) {
            throw new IllegalArgumentException("Transaction declined: Credit card has expired.");
        }

        BankAccount fromAccount = card.getBankAccount();
        if (fromAccount == null) {
            throw new BussinesException("System error: Credit card is not linked to any active bank account.");
        }




        BankAccount toAccount = bankAccountRepository.findByIban(payment.getDestinationIban())
                .orElseThrow(() -> new BussinesException("Transaction failed: Destination account IBAN not found or does not exist."));

        BigDecimal amount = payment.getAmount();
        if (fromAccount.getBalance() == null) {
            fromAccount.setBalance(BigDecimal.ZERO);
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transaction failed: Amount must be greater than zero.");
        }
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Transaction declined: Insufficient funds in the linked bank account.");
        }

        // Deduct from origin
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        bankAccountRepository.save(fromAccount);

        String fromConcept = (payment.getConcept() != null && !payment.getConcept().isEmpty()) ? payment.getConcept() : "Card Payment to " + toAccount.getIban();
        createTransaction(fromAccount, amount, fromConcept, TransactionType.DEBIT, TransactionOrigin.CARD);

        // Add to destination
        if (toAccount.getBalance() == null) {
            toAccount.setBalance(BigDecimal.ZERO);
        }
        toAccount.setBalance(toAccount.getBalance().add(amount));
        bankAccountRepository.save(toAccount);

        String toConcept = (payment.getConcept() != null && !payment.getConcept().isEmpty()) ? payment.getConcept() : "Card Payment from " + fromAccount.getIban();
        createTransaction(toAccount, amount, toConcept, TransactionType.CREDIT, TransactionOrigin.CARD);
    }

    private void createTransaction(BankAccount account, BigDecimal amount, String concept, TransactionType type, TransactionOrigin origin) {
        BankTransaction transaction = new BankTransaction();
        transaction.setBankAccount(account);
        transaction.setAmount(amount);
        transaction.setConcept(concept);
        transaction.setDate(LocalDateTime.now().toString());
        transaction.setType(type);
        transaction.setOrigin(origin);
        bankTransactionRepository.save(transaction);
    }
    
}
