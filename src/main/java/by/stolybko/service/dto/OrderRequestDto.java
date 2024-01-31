package by.stolybko.service.dto;

import java.util.List;
import java.util.UUID;

public record OrderRequestDto(UUID customerId, List<ProductUuidRequestDto> products) {
}
