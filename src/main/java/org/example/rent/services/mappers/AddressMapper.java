package org.example.rent.services.mappers;

import org.example.rent.dto.AddressDTO;
import org.example.rent.dto.BuildingDTO;
import org.example.rent.dto.propertydto.PropertyDTO;
import org.example.rent.entity.Address;
import org.example.rent.entity.Building;
import org.example.rent.entity.property.Property;
import org.example.rent.services.mappers.property.PropertyMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mapping(target = "id", ignore = true)
    Address toEntity(AddressDTO dto);

    AddressDTO toDto(Address entity);
}
