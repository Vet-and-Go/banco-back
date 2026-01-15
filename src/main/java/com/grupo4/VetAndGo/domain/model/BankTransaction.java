package com.grupo4.VetAndGo.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BankTransaction {
    private Long id;
    private String date;
    private BigDecimal amount;
    private String concept;
    private TransactionType type;
    private TransactionOrigin origin;
    private String cardNumber;
    private BankAccount bankAccount;

    public BankTransaction() {
    }

    public BankTransaction(Long id, String date, BigDecimal amount, String concept, TransactionType type, TransactionOrigin origin, String cardNumber, BankAccount bankAccount) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.concept = concept;
        this.type = type;
        this.origin = origin;
        this.cardNumber = cardNumber;
        this.bankAccount = bankAccount;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getConcept() { return concept; }
    public void setConcept(String concept) { this.concept = concept; }

    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }

    public TransactionOrigin getOrigin() { return origin; }
    public void setOrigin(TransactionOrigin origin) { this.origin = origin; }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public BankAccount getBankAccount() { return bankAccount; }
    public void setBankAccount(BankAccount bankAccount) { this.bankAccount = bankAccount; }
}
