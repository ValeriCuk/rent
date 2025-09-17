package org.example.rent.services.mappers;

import org.example.rent.dto.OrderDTO;
import org.example.rent.entity.User;
import org.example.rent.dto.UserDTO;
import org.example.rent.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ordersDTO", ignore = true)
    User toEntity(UserDTO dto);

    default User toEntityWithRelations(UserDTO dto) {
        User entity = toEntity(dto);
        if (!dto.getOrdersDTO().isEmpty()) {
            List<Order> orders = dto.getOrdersDTO().stream()
                    .map(orderMapper()::toEntityWithRelations)
                    .toList();
            entity.setOrders(orders);
        }
        return entity;
    }

    @Mapping(target = "orders", ignore = true)
    UserDTO toDto(User user);

    default UserDTO toDtoWithRelations(User entity) {
        UserDTO dto = toDto(entity);
        if (!entity.getOrders().isEmpty()) {
            List<OrderDTO> orders = entity.getOrders().stream()
                    .map(orderMapper()::toDtoWithRelations)
                    .toList();
            dto.setOrdersDTO(orders);
        }
        return dto;
    }

    OrderMapper orderMapper();
}
