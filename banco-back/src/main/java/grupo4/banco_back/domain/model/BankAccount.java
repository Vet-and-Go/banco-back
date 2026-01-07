package grupo4.banco_back.domain.model;

public class BankAccount {
  long id;
  String iban;
  double balance;
  String clientDni;

  public BankAccount(long id, String iban, double balance, String clientDni) {
    this.id = id;
    this.iban = iban;
    this.balance = balance;
    this.clientDni = clientDni;
  }

  public BankAccount() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getIban() {
    return iban;
  }

  public void setIban(String iban) {
    this.iban = iban;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public String getClientDni() {
    return clientDni;
  }

  public void setClientDni(String clientDni) {
    this.clientDni = clientDni;
  }

}
