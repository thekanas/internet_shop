package by.stolybko.service.mapper;

import by.stolybko.model.Customer;
import by.stolybko.service.dto.CustomerRequestDto;
import by.stolybko.service.dto.CustomerResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerDtoMapper {

    Customer toEntity(CustomerRequestDto customerRequestDto);

    CustomerResponseDto toDto(Customer customer);

}
