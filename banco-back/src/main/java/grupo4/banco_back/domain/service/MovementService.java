package grupo4.banco_back.domain.service;

import grupo4.banco_back.domain.dto.MovementDto;

import java.util.List;

public interface MovementService {
  // Are login & apiToken realted in database?
  Boolean validateStore(
      String login,
      String apiToken);

  // Can the origin account afford the payment?
  Boolean validateAblePayment(
      String cardNumber,
      Double amount);

  List<MovementDto> getAll();
}
