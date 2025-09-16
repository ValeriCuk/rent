package org.example.rent.services.mappers;

import org.mapstruct.Mapping;
import org.mapstruct.Mapper;
import org.example.rent.entity.property.Property;
import org.example.rent.dto.propertydto.PropertyDTO;

@Mapper(componentModel = "spring", uses = {AddressMapper.class, PhotoMapper.class, OrderMapper.class})
public interface PropertyMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "photos", ignore = true)
    Property toEntity(PropertyDTO dto);

    PropertyDTO toDto(Property entity);
}
