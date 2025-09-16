package org.example.rent.services.mappers.property;

import org.example.rent.dto.propertydto.CommercialDTO;
import org.example.rent.entity.property.Commercial;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PropertyMapper.class})
public interface CommercialMapper {

    @Mapping(target = "id", ignore = true)
    Commercial toEntity(CommercialDTO dto);

    default Commercial toEntityWithRelations(CommercialDTO dto) {
        Commercial entity = (Commercial) propertyMapper().toEntityWithRelations(dto);

        entity.setFloor(dto.getFloor());
        entity.setRooms(dto.getRooms());

        return entity;
    }

    CommercialDTO toDTO(Commercial entity);

    default CommercialDTO toDTOWithRelations(Commercial entity) {
        CommercialDTO dto = (CommercialDTO) propertyMapper().toDTOWithRelations(entity);
        dto.setFloor(entity.getFloor());
        dto.setRooms(entity.getRooms());
        return dto;
    }

    PropertyMapper propertyMapper();
}
