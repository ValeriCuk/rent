package org.example.rent.services.mappers;

import org.example.rent.dto.AddressDTO;
import org.example.rent.dto.BuildingDTO;
import org.example.rent.entity.Address;
import org.example.rent.entity.Building;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(componentModel = "spring", uses = {
        AddressMapper.class,
        LocationMapper.class})
public abstract class BuildingMapper {

    @Autowired
    AddressMapper addressMapper;
    @Autowired
    LocationMapper locationMapper;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", ignore = true)
    public abstract Building toEntity(BuildingDTO dto);

    public Building toEntityWithRelations(BuildingDTO dto) {
        Building entity = toEntity(dto);

        Address address = addressMapper.toEntity(dto.getAddress());
        entity.setAddress(address);
        return entity;
    }

    @Mapping(target = "address", ignore = true)
    public abstract BuildingDTO toDto(Building entity);

    public BuildingDTO toDtoWithRelations(Building entity) {
        BuildingDTO dto = toDto(entity);
        AddressDTO address = addressMapper.toDto(entity.getAddress());
        dto.setAddress(address);

        return dto;
    }
}
