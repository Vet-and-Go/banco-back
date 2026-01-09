package com.grupo4.VetAndGo.domain.model;

import java.time.LocalDate;

public class CreditCard {
    private Long id;
    private String cardNumber;
    private String expirationDate;
    private String cvc;
    private String fullName;
    private BankAccount bankAccount;

    public CreditCard() {
    }

    public CreditCard(Long id, String cardNumber, String expirationDate, String cvc, String fullName, BankAccount bankAccount) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvc = cvc;
        this.fullName = fullName;
        this.bankAccount = bankAccount;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getExpirationDate() { return expirationDate; }
    public void setExpirationDate(String expirationDate) { this.expirationDate = expirationDate; }

    public String getCvc() { return cvc; }
    public void setCvc(String cvc) { this.cvc = cvc; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public BankAccount getBankAccount() { return bankAccount; }
    public void setBankAccount(BankAccount bankAccount) { this.bankAccount = bankAccount; }
}
