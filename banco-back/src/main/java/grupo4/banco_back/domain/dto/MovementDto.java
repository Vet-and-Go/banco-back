package grupo4.banco_back.domain.dto;

import grupo4.banco_back.domain.enums.*;
import grupo4.banco_back.domain.model.CreditCard;

import java.math.BigDecimal;
import java.util.Date;

public record MovementDto(
    Long id,
    MOVEMENT_TYPE type,
    MOVEMENT_ORIGIN origin,
    CreditCard creditCard,
    Date date,
    BigDecimal amount,
    String concept

) {
  public MovementDto(
      Long id,
      MOVEMENT_TYPE type,
      MOVEMENT_ORIGIN origin,
      CreditCard creditCard,
      Date date,
      BigDecimal amount,
      String concept) {
    this.id = id;
    this.type = type;
    this.origin = origin;
    this.creditCard = creditCard;
    this.date = date;
    this.amount = amount;
    this.concept = concept;
  }
}
