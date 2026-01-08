package grupo4.banco_back.domain.repository;

import java.math.BigDecimal;
import java.util.Date;

public interface MovementRepository {
  Boolean validateStore(
      String login,
      String apiToken);

  Boolean validateCard(
      String login,
      String cardNumber,
      Date expirationDate,
      String cvv,
      String fullName);

  Boolean validateAccountRelation(
      String login,
      String destinationIban);

  Boolean validateAblePayment(
      String cardNumber,
      Double amount);

  void processPayment(
      String cardNumber,
      String destinationIban,
      BigDecimal amount,
      String concept);

  // NOT VOID, MUST BE LIST OF ..?..
  void findAll();
}
