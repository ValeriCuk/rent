package org.example.rent.services.mappers;

import org.example.rent.dto.AddressDTO;
import org.example.rent.dto.BuildingDTO;
import org.example.rent.dto.PhotoDTO;
import org.example.rent.dto.propertydto.PropertyDTO;
import org.example.rent.entity.Address;
import org.example.rent.entity.Building;
import org.example.rent.entity.Photo;
import org.example.rent.entity.property.Property;
import org.example.rent.services.mappers.property.PropertyMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        AddressMapper.class,
        PropertyMapper.class})
public interface BuildingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "addressDTO", ignore = true)
    Building toEntity(BuildingDTO dto);

    default Building toEntityWithRelations(BuildingDTO dto) {
        Building entity = toEntity(dto);

        Address address = addressMapper().toEntity(dto.getAddressDTO());
        entity.setAddress(address);
        return entity;
    }

    @Mapping(target = "address", ignore = true)
    BuildingDTO toDto(Building entity);

    default BuildingDTO toDtoWithRelations(Building entity) {
        BuildingDTO dto = toDto(entity);
        AddressDTO address = addressMapper().toDto(entity.getAddress());
        dto.setAddressDTO(address);

        return dto;
    }

    AddressMapper addressMapper();
}
