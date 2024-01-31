package by.stolybko.service.dto;

import by.stolybko.model.Product;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponseDtoWithProduct(UUID id,
                                          UUID customerId,

                                          @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
                                          LocalDateTime createDate,
                                          List<ProductResponseDto> products) {
}
