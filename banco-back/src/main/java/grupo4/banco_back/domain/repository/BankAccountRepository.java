package grupo4.banco_back.domain.repository;

import java.math.BigDecimal;

public interface BankAccountRepository {
  void deposit(String iban, BigDecimal amount);

  void withdraw(String iban, BigDecimal amount);
}
