package grupo4.banco_back.domain.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import grupo4.banco_back.domain.repository.PaymentRepository;
import grupo4.banco_back.domain.service.PaymentService;

public class PaymentServiceImpl implements PaymentService {

  private final PaymentRepository paymentRepository;

  public PaymentServiceImpl(PaymentRepository paymentRepository) {
    this.paymentRepository = paymentRepository;
  }

  @Override
  public void processPayment(String cardNumber, String destinationIban, BigDecimal amount, String concept) {

  }

  @Override
  public Boolean validateAblePayment(String cardNumber, Double amount) {

    return null;
  }

  @Override
  public Boolean validateAccountRelation(String login, String destinationIban) {

    return null;
  }

  @Override
  public Boolean validateCard(String login, String cardNumber, Date expirationDate, String cvv, String fullName) {
    return null;
  }

  @Override
  public Boolean validateStore(String login, String apiToken) {
    return null;
  }

}
