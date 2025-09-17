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
        PropertyMapper.class,
        PhotoMapper.class,
        PropertyMapper.class})
public interface BuildingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "addressDTO", ignore = true)
    @Mapping(target = "propertyListDTO", ignore = true)
    @Mapping(target = "photosDTO", ignore = true)
    Building toEntity(BuildingDTO dto);

    default Building toEntityWithRelations(BuildingDTO dto) {
        Building entity = toEntity(dto);

        Address address = addressMapper().toEntityWithRelations(dto.getAddressDTO());
        entity.setAddress(address);

        if (!dto.getPhotosDTO().isEmpty()){
            List<Photo> photos = dto.getPhotosDTO().stream()
                    .map(photoMapper()::toEntityWithRelations)
                    .toList();
            entity.setPhotos(photos);
        }

        if (!dto.getPropertyListDTO().isEmpty()){
            List<Property> properties = dto.getPropertyListDTO().stream()
                    .map(propertyMapper()::toEntityWithRelations)
                    .toList();
            entity.setPropertyList(properties);
        }
        return entity;
    }

    @Mapping(target = "address", ignore = true)
    @Mapping(target = "propertyList", ignore = true)
    @Mapping(target = "photos", ignore = true)
    BuildingDTO toDto(Building entity);

    default BuildingDTO toDtoWithRelations(Building entity) {
        BuildingDTO dto = toDto(entity);
        AddressDTO address = addressMapper().toDtoWithRelations(entity.getAddress());
        dto.setAddressDTO(address);

        if (!entity.getPhotos().isEmpty()){
            List<PhotoDTO> photos = entity.getPhotos().stream()
                    .map(photoMapper()::toDtoWithRelations)
                    .toList();
            dto.setPhotosDTO(photos);
        }

        if (!entity.getPropertyList().isEmpty()){
            List<PropertyDTO> apartments = entity.getPropertyList().stream()
                    .map(propertyMapper()::toDTOWithRelations)
                    .toList();
            dto.setPropertyListDTO(apartments);
        }
        return dto;
    }

    AddressMapper addressMapper();
    PhotoMapper photoMapper();
    PropertyMapper propertyMapper();
}
