package org.example.rent.services.mappers;

import org.example.rent.dto.AddressDTO;
import org.example.rent.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mapping(target = "id", ignore = true)
    Address toEntity(AddressDTO dto);

    AddressDTO toDto(Address entity);
}
