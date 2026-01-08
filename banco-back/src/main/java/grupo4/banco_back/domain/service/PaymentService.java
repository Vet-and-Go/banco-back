package grupo4.banco_back.domain.service;

import grupo4.banco_back.domain.dto.PaymentDto;

import java.util.List;

public interface PaymentService {
  List<PaymentDto> getAll();
}
