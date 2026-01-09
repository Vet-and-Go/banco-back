package grupo4.banco_back.domain.service.cases;

import grupo4.banco_back.domain.dto.MovementDto;

import java.util.List;

public interface GetAllMovementsCase {

  List<MovementDto> getAll();

}
