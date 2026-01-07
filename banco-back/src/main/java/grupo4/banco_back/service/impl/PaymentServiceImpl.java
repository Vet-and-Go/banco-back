package grupo4.banco_back.service.impl;

import java.util.Date;

import grupo4.banco_back.service.PaymentService;

public class PaymentServiceImpl implements PaymentService {

  @Override
  public Boolean validateCard(String login, String cardNumber, Date expirationDate, String cvv, String fullName) {
    return null;
  }

  @Override
  public Boolean validateCorrectAccountRelation(String login, String destinationIban) {
    return null;
  }

  @Override
  public Boolean validatePossiblePayment(String cardNumber, Double amount) {
    return null;
  }

  @Override
  public Boolean validateStore(String login, String apiToken) {
    return null;
  }

}
