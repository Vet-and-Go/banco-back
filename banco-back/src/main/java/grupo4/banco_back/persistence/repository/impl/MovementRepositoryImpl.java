package grupo4.banco_back.persistence.repository.impl;

import java.util.List;

import grupo4.banco_back.domain.dto.MovementDto;
import grupo4.banco_back.domain.repository.MovementRepository;

import jakarta.transaction.Transactional;

@Transactional
public class MovementRepositoryImpl implements MovementRepository {

  @Override
  public List<MovementDto> getAll() {
    throw new UnsupportedOperationException("Unimplemented method 'getAll'");
  }
}
