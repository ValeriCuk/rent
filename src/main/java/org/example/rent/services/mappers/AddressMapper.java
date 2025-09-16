package org.example.rent.services.mappers;

import org.example.rent.dto.AddressDTO;
import org.example.rent.entity.Address;
import org.example.rent.services.mappers.property.PropertyMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PropertyMapper.class, BuildingMapper.class})
public interface AddressMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "building", ignore = true)
    @Mapping(target = "property", ignore = true)
    Address toEntity(AddressDTO dto);

    @Mapping(target = "building", ignore = true)
    @Mapping(target = "property", ignore = true)
    AddressDTO toDto(Address entity);
}
