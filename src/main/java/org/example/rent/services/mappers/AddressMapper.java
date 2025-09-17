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

@Mapper(componentModel = "spring", uses = {
        PropertyMapper.class,
        BuildingMapper.class})
public interface AddressMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "buildingDTO", ignore = true)
    @Mapping(target = "propertyDTO", ignore = true)
    Address toEntity(AddressDTO dto);

    default Address toEntityWithRelations(AddressDTO dto) {
        Address entity = toEntity(dto);
        Property property = propertyMapper().toEntityWithRelations(dto.getPropertyDTO());
        Building building = buildingMapper().toEntityWithRelations(dto.getBuildingDTO());
        entity.setProperty(property);
        entity.setBuilding(building);
        return entity;
    }

    @Mapping(target = "building", ignore = true)
    @Mapping(target = "property", ignore = true)
    AddressDTO toDto(Address entity);

    default AddressDTO toDtoWithRelations(Address entity) {
        AddressDTO dto = toDto(entity);
        PropertyDTO property = propertyMapper().toDTOWithRelations(entity.getProperty());
        BuildingDTO building = buildingMapper().toDtoWithRelations(entity.getBuilding());
        dto.setPropertyDTO(property);
        dto.setBuildingDTO(building);
        return dto;
    }

    PropertyMapper propertyMapper();
    BuildingMapper buildingMapper();
}
