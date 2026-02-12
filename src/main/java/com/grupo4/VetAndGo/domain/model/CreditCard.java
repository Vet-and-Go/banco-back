package com.grupo4.VetAndGo.domain.model;

import java.time.LocalDate;
import java.time.YearMonth;

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

    public YearMonth getYearMonth() {
        if (expirationDate == null) {
            throw new IllegalArgumentException("System error: Stored card has no expiration date.");
        }
        
        // Support YYYY-MM format (stored in database)
        if (expirationDate.matches("^\\d{4}-\\d{2}$")) {
            int year = Integer.parseInt(expirationDate.substring(0, 4));
            int month = Integer.parseInt(expirationDate.substring(5, 7));
            return YearMonth.of(year, month);
        }
        
        // Support YYYY-MM-DD format (full date in database)
        if (expirationDate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            int year = Integer.parseInt(expirationDate.substring(0, 4));
            int month = Integer.parseInt(expirationDate.substring(5, 7));
            return YearMonth.of(year, month);
        }
        
        // Support MM/YY format
        if (expirationDate.matches("^\\d{2}/\\d{2}$")) {
            int month = Integer.parseInt(expirationDate.substring(0, 2));
            int year = 2000 + Integer.parseInt(expirationDate.substring(3, 5));
            return YearMonth.of(year, month);
        }
        
        throw new IllegalArgumentException("System error: Invalid stored card date format: " + expirationDate);
    }

    public boolean isExpired() {
        return getYearMonth().isBefore(YearMonth.now());
    }
}
