package grupo4.banco_back.domain.mapper;

import grupo4.banco_back.domain.model.Movement;
import grupo4.banco_back.domain.dto.MovementDto;

public class MovementMapper {

  public Movement fromMovementDtoToMovement(MovementDto movementDto) {
    if (movementDto == null) {
      return null;
    }
    return new Movement(
        movementDto.id(),
        movementDto.type(),
        movementDto.origin(),
        movementDto.creditCard(),
        movementDto.date(),
        movementDto.amount(),
        movementDto.concept());
  }

}
