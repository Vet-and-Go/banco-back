package grupo4.banco_back.domain.mapper;

import grupo4.banco_back.domain.dto.MovementDto;
import grupo4.banco_back.domain.model.Movement;
import grupo4.banco_back.persistence.dao.jpa.MovementJpaEntity;

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

  public MovementDto fromMovementToMovementDto(Movement movement) {
    if (movement == null) {
      return null;
    }
    return new MovementDto(
        movement.getId(),
        movement.getType(),
        movement.getOrigin(),
        movement.getCreditCard(),
        movement.getDate(),
        movement.getAmount(),
        movement.getConcept());
  }

  public MovementJpaEntity fromMovementToMovementJpaEntity(Movement movement) {
    if (movement == null) {
      return null;
    }
    MovementJpaEntity movementJpaEntity = new MovementJpaEntity();
    movementJpaEntity.setId(movement.getId());
    movementJpaEntity.setType(movement.getType());
    movementJpaEntity.setOrigin(movement.getOrigin());
    movementJpaEntity.setCreditCard(movement.getCreditCard());
    movementJpaEntity.setDate(movement.getDate());
    movementJpaEntity.setAmount(movement.getAmount());
    movementJpaEntity.setConcept(movement.getConcept());
    return movementJpaEntity;
  }

  public Movement fromMovementJpaEntityToMovement(MovementJpaEntity movementJpaEntity) {
    if (movementJpaEntity == null) {
      return null;
    }
    return new Movement(
        movementJpaEntity.getId(),
        movementJpaEntity.getType(),
        movementJpaEntity.getOrigin(),
        movementJpaEntity.getCreditCard(),
        movementJpaEntity.getDate(),
        movementJpaEntity.getAmount(),
        movementJpaEntity.getConcept());
  }
}
