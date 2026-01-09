package com.grupo4.VetAndGo.domain.model;

import java.math.BigDecimal;

public class BankAccount {
    private Long id;
    private BigDecimal balance;
    private String iban;
    private Client client;

    public BankAccount() {
    }

    public BankAccount(Long id, BigDecimal balance, String iban, Client client) {
        this.id = id;
        this.balance = balance;
        this.iban = iban;
        this.client = client;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public String getIban() { return iban; }
    public void setIban(String iban) { this.iban = iban; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
}
