package org.example.rent.services.mappers;

import org.example.rent.dto.OrderDTO;
import org.example.rent.dto.UserDTO;
import org.example.rent.dto.propertydto.PropertyDTO;
import org.example.rent.entity.Order;
import org.example.rent.services.mappers.property.PropertyMapper;
import org.example.rent.entity.User;
import org.example.rent.entity.property.Property;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" , uses = {
        PropertyMapper.class,
        UserMapper.class})
public interface OrderMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "property", ignore = true)
    Order toEntity(OrderDTO dto);

    default Order toEntityWithRelations(OrderDTO dto) {
        Order entity = toEntity(dto);
        User user = userMapper().toEntity(dto.getUser());
        entity.setUser(user);
        if (dto.getProperty() != null) {
            Property property = propertyMapper().toEntityWithRelations(dto.getProperty());
            entity.setProperty(property);
        }
        return entity;
    }

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "property", ignore = true)
    OrderDTO toDto(Order order);

    default OrderDTO toDtoWithRelations(Order entity){
        OrderDTO dto = toDto(entity);
        UserDTO user = userMapper().toDto(entity.getUser());
        dto.setUser(user);
        if (entity.getProperty() != null) {
            PropertyDTO property = propertyMapper().toDTOWithRelations(entity.getProperty());
            dto.setProperty(property);
        }
        return dto;
    }

    PropertyMapper propertyMapper();
    UserMapper userMapper();
}
