package org.example.rent.services.mappers;

import org.example.rent.dto.OrderDTO;
import org.example.rent.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "id", ignore = true)
    Order toEntity(OrderDTO dto);

    OrderDTO toDto(Order order);
}
