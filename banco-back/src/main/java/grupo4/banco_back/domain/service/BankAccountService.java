package grupo4.banco_back.domain.service;

public interface BankAccountService {
  void deposit(String iban, Double amount);

  void withdraw(String iban, Double amount);
}
