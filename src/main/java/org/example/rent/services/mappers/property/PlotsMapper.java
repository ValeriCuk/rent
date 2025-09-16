package org.example.rent.services.mappers.property;

import org.example.rent.dto.propertydto.PlotsDTO;
import org.example.rent.entity.property.Plots;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PropertyMapper.class})
public interface PlotsMapper {

    @Mapping(target = "id", ignore = true)
    Plots toEntity(PlotsDTO dto);

    default Plots toEntityWithRelations(PlotsDTO dto) {
        return (Plots) propertyMapper().toEntityWithRelations(dto);
    }

    PlotsDTO toDto(Plots entity);

    default  PlotsDTO toDtoWithRelations(Plots entity) {
        return (PlotsDTO) propertyMapper().toDto(entity);
    }

    PropertyMapper propertyMapper();
}
