package org.example.rent.services.mappers.property;

import org.example.rent.dto.AddressDTO;
import org.example.rent.dto.BuildingDTO;
import org.example.rent.dto.PhotoDTO;
import org.example.rent.dto.propertydto.ApartmentDTO;
import org.example.rent.dto.propertydto.CommercialDTO;
import org.example.rent.dto.propertydto.HouseDTO;
import org.example.rent.dto.propertydto.PlotsDTO;
import org.example.rent.entity.Address;
import org.example.rent.entity.Building;
import org.example.rent.entity.Photo;
import org.example.rent.entity.property.Apartment;
import org.example.rent.entity.property.House;
import org.example.rent.entity.property.Commercial;
import org.example.rent.entity.property.Plots;
import org.example.rent.other.PropertyFactory;
import org.example.rent.services.mappers.AddressMapper;
import org.example.rent.services.mappers.BuildingMapper;
import org.example.rent.services.mappers.LocationMapper;
import org.example.rent.services.mappers.PhotoMapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;
import org.example.rent.entity.property.Property;
import org.example.rent.dto.propertydto.PropertyDTO;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        AddressMapper.class,
        PhotoMapper.class,
        ApartmentMapper.class,
        CommercialMapper.class,
        HouseMapper.class,
        PlotsMapper.class,
        BuildingMapper.class,
        LocationMapper.class,
        PropertyFactory.class
})
public abstract class PropertyMapper {

    @Autowired
    AddressMapper addressMapper;
    @Autowired
    PhotoMapper photoMapper;
    @Autowired
    BuildingMapper buildingMapper;
    @Autowired
    ApartmentMapper apartmentMapper;
    @Autowired
    CommercialMapper commercialMapper;
    @Autowired
    HouseMapper houseMapper;
    @Autowired
    PlotsMapper plotsMapper;
    @Autowired
    LocationMapper locationMapper;
    @Autowired
    PropertyFactory propertyFactory;

    //DTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "photos", ignore = true)
    @Mapping(target = "building", ignore = true)
    public abstract Property toEntity(PropertyDTO dto);

    public Property toEntityWithRelations(PropertyDTO dto) {
        Property entity = mapToEntity(dto);

        //AddressDTO -> Address
        if (dto.getAddress() != null) {
            Address address = addressMapper.toEntity(dto.getAddress());
            entity.setAddress(address);
        }
        //List<PhotoDTO> -> List<Photo>
        if (!dto.getPhotos().isEmpty()) {
            List<Photo> photos = dto.getPhotos().stream()
                    .map(photoMapper::toEntity)
                    .toList();
            entity.setPhotos(photos);
        }
        //BuildingDTO -> Building
        if (dto.getBuilding() != null) {
            Building building = buildingMapper.toEntityWithRelations(dto.getBuilding());
            entity.setBuilding(building);
        }
        return entity;
    }
    //find type of property
    public Property mapToEntity(PropertyDTO dto) {
        if (dto instanceof ApartmentDTO apartmentDTO) {
            return apartmentMapper.toEntityWithRelations(apartmentDTO, this);
        }else if (dto instanceof CommercialDTO commercialDTO) {
            return commercialMapper.toEntityWithRelations(commercialDTO, this);
        } else if (dto instanceof HouseDTO houseDTO) {
            return houseMapper.toEntityWithRelations(houseDTO, this);
        } else if (dto instanceof PlotsDTO plotsDTO) {
            return plotsMapper.toEntityWithRelations(plotsDTO, this);
        } else {
            return toEntity(dto);
        }
    }

    //Entity -> DTO
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "photos", ignore = true)
    @Mapping(target = "building", ignore = true)
    public abstract PropertyDTO toDto(Property entity);

    public PropertyDTO toDTOWithRelations(Property entity) {
        PropertyDTO dto = mapToDTO(entity);

        //Address -> AddressDTO
        if (entity.getAddress() != null) {
            AddressDTO address = addressMapper.toDto(entity.getAddress());
            dto.setAddress(address);
        }
        //List<Photo> -> List<PhotoDTO>
        if (!entity.getPhotos().isEmpty()) {
            List<PhotoDTO> photos = entity.getPhotos().stream()
                    .map(photoMapper::toDto)
                    .toList();
            dto.setPhotos(photos);
        }
        //Building -> BuildingDTO
        if (entity.getBuilding() != null) {
            BuildingDTO building =  buildingMapper.toDtoWithRelations(entity.getBuilding());
            dto.setBuilding(building);
        }
        return dto;
    }

    public PropertyDTO mapToDTO(Property entity) {
        if (entity instanceof Apartment apartment) {
            return apartmentMapper.toDTOWithRelations(apartment, this);
        }else if (entity instanceof Commercial commercial) {
            return commercialMapper.toDTOWithRelations(commercial, this);
        } else if (entity instanceof House house) {
            return houseMapper.toDtoWithRelations(house, this);
        } else if (entity instanceof Plots plots) {
            return plotsMapper.toDtoWithRelations(plots, this);
        } else {
            return toDto(entity);
        }
    }

    @ObjectFactory
    public Property resolvePropertyType(PropertyDTO dto) {
        return propertyFactory.create(dto);
    }

    @ObjectFactory
    public PropertyDTO resolveDTOType(Property entity) {
        return propertyFactory.createDTO(entity);
    }
}
