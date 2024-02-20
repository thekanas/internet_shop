package by.stolybko.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderResponseDto(UUID id,
                               UUID customerId,

                               @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
                               LocalDateTime createDate) {
}


