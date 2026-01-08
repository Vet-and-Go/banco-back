package grupo4.banco_back.persistence.dao.jpa;

import jakarta.persistence.*;

@Entity
@Table(name = "bank_accounts")
public class BankAccountJpaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  @Column(name = "iban")
  String iban;
  @Column(name = "balance")
  Double balance;
  @Column(name = "clientDni")
  String clientDni;

  public BankAccountJpaEntity() {
  }

  public BankAccountJpaEntity(Long id, String iban, Double balance, String clientDni) {
    this.id = id;
    this.iban = iban;
    this.balance = balance;
    this.clientDni = clientDni;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getIban() {
    return iban;
  }

  public void setIban(String iban) {
    this.iban = iban;
  }

  public Double getBalance() {
    return balance;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
  }

  public String getClientDni() {
    return clientDni;
  }

  public void setClientDni(String clientDni) {
    this.clientDni = clientDni;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof ClientJpaEntity other)) {
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
