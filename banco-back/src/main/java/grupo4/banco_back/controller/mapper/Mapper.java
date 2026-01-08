package grupo4.banco_back.controller.mapper;

import java.math.BigDecimal;

import grupo4.banco_back.domain.dto.PaymentDto;
import java.util.Date;

public class Mapper {
  public PaymentDto fromPaymentRequestToPaymentDto(
      String login,
      String apiToken,
      String CardNumber,
      Date expirationDate,
      String cvv,
      String fullName,
      String destinationIban,
      BigDecimal amount,
      String concept) {

    return new PaymentDto(
        login,
        apiToken,
        CardNumber,
        expirationDate,
        cvv,
        fullName,
        destinationIban,
        amount,
        concept);
  }
}
