package com.grupo4.VetAndGo.persistence.dao.jpa.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "bank_accounts")
public class BankAccountJpaEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance;
    
    @Column(unique = true)
    private String iban;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private ClientJpaEntity client;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CreditCardJpaEntity> creditCards;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BankTransactionJpaEntity> transactions;

    public BankAccountJpaEntity() {
    }

    public BankAccountJpaEntity(Long id, BigDecimal balance, String iban, ClientJpaEntity client, List<CreditCardJpaEntity> creditCards, List<BankTransactionJpaEntity> transactions) {
        this.id = id;
        this.balance = balance;
        this.iban = iban;
        this.client = client;
        this.creditCards = creditCards;
        this.transactions = transactions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public ClientJpaEntity getClient() {
        return client;
    }

    public void setClient(ClientJpaEntity client) {
        this.client = client;
    }

    public List<CreditCardJpaEntity> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(List<CreditCardJpaEntity> creditCards) {
        this.creditCards = creditCards;
    }

    public List<BankTransactionJpaEntity> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<BankTransactionJpaEntity> transactions) {
        this.transactions = transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BankAccountJpaEntity other)) {
            return false;
        }
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
}
