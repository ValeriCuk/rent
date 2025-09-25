package org.example.rent.services.mappers.property;

import org.example.rent.dto.propertydto.HouseDTO;
import org.example.rent.entity.property.House;
import org.example.rent.services.mappers.LocationMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {
        PropertyMapper.class,
        LocationMapper.class})
public abstract class HouseMapper {
    @Autowired
    LocationMapper locationMapper;

    @Mapping(target = "id", ignore = true)
    public abstract House toEntity(HouseDTO dto);

    public House toEntityWithRelations(HouseDTO dto, PropertyMapper propertyMapper) {
        House entity = (House) propertyMapper.toEntity(dto);

        entity.setBedrooms(dto.getBedrooms());
        entity.setFloors(dto.getFloors());
        entity.setOutsideArea(dto.getOutsideArea());
        return entity;
    }

    public abstract HouseDTO toDto(House entity);

    public HouseDTO toDtoWithRelations(House entity, PropertyMapper propertyMapper) {
        HouseDTO dto = (HouseDTO) propertyMapper.toDto(entity);

        dto.setBedrooms(entity.getBedrooms());
        dto.setFloors(entity.getFloors());
        dto.setOutsideArea(entity.getOutsideArea());
        return dto;
    }
}
