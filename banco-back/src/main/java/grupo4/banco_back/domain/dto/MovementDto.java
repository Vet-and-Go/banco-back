package grupo4.banco_back.domain.dto;

import grupo4.banco_back.domain.enums.*;
import grupo4.banco_back.domain.model.CreditCard;

import java.math.BigDecimal;
import java.util.Date;

public record MovementDto(
    MOVEMENT_TYPE type,
    MOVEMENT_ORIGIN origin,
    CreditCard creditCard,
    Date date,
    BigDecimal amount,
    String concept

) {
}
