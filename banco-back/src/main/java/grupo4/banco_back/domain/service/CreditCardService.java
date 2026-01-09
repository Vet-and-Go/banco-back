package grupo4.banco_back.domain.service;

import java.util.Date;

public interface CreditCardService {
  // Is the card in a valid format?
  Boolean validateCard(
      String login,
      String cardNumber,
      Date expirationDate,
      String cvv,
      String fullName);
}
