package grupo4.banco_back.domain.service.impl;

import java.util.List;

import grupo4.banco_back.domain.dto.MovementDto;
import grupo4.banco_back.domain.service.MovementService;

public class CreditCardServiceImpl implements MovementService {

  @Override
  public Boolean validateStore(String login, String apiToken) {
    throw new UnsupportedOperationException("Unimplemented method 'validateStore'");
  }

  @Override
  public Boolean validateAblePayment(String cardNumber, Double amount) {
    throw new UnsupportedOperationException("Unimplemented method 'validateAblePayment'");
  }

  @Override
  public List<MovementDto> getAll() {
    throw new UnsupportedOperationException("Unimplemented method 'getAll'");
  }

}
