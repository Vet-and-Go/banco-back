package grupo4.banco_back.domain.service.impl;

import grupo4.banco_back.domain.service.BankAccountService;

public class BankAccountServiceImpl implements BankAccountService {

  @Override
  public void deposit(String iban, Double amount) {
    throw new UnsupportedOperationException("Unimplemented method 'deposit'");
  }

  @Override
  public void withdraw(String iban, Double amount) {
    throw new UnsupportedOperationException("Unimplemented method 'withdraw'");
  }

  @Override
  public Boolean validateAccountRelation(String login, String destinationIban) {
    throw new UnsupportedOperationException("Unimplemented method 'validateAccountRelation'");
  }

}
