package org.example.rent.services.mappers.property;

import org.example.rent.dto.propertydto.HouseDTO;
import org.example.rent.entity.property.House;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PropertyMapper.class})
public interface HouseMapper {

    @Mapping(target = "id", ignore = true)
    House toEntity(HouseDTO dto);

    default House toEntityWithRelations(HouseDTO dto) {
        House entity = (House) propertyMapper().toEntityWithRelations(dto);
        entity.setBedrooms(dto.getBedrooms());
        entity.setFloors(dto.getFloors());
        entity.setOutsideArea(dto.getOutsideArea());
        return entity;
    }

    HouseDTO toDto(House entity);

    default HouseDTO toDtoWithRelations(House entity) {
        HouseDTO dto = (HouseDTO) propertyMapper().toDto(entity);
        dto.setBedrooms(entity.getBedrooms());
        dto.setFloors(entity.getFloors());
        dto.setOutsideArea(entity.getOutsideArea());
        return dto;
    }

    PropertyMapper propertyMapper();
}
