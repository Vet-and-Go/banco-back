package grupo4.banco_back.domain.repository;

import grupo4.banco_back.domain.dto.MovementDto;

import java.util.List;

public interface MovementRepository {
  List<MovementDto> getAll();
}
