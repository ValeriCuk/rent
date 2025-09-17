package org.example.rent.services.mappers.property;

import org.example.rent.dto.AddressDTO;
import org.example.rent.dto.BuildingDTO;
import org.example.rent.dto.OrderDTO;
import org.example.rent.dto.PhotoDTO;
import org.example.rent.dto.propertydto.ApartmentDTO;
import org.example.rent.dto.propertydto.CommercialDTO;
import org.example.rent.dto.propertydto.HouseDTO;
import org.example.rent.dto.propertydto.PlotsDTO;
import org.example.rent.entity.Address;
import org.example.rent.entity.Building;
import org.example.rent.entity.Order;
import org.example.rent.entity.Photo;
import org.example.rent.entity.property.Apartment;
import org.example.rent.entity.property.House;
import org.example.rent.entity.property.Commercial;
import org.example.rent.entity.property.Plots;
import org.example.rent.services.mappers.AddressMapper;
import org.example.rent.services.mappers.BuildingMapper;
import org.example.rent.services.mappers.OrderMapper;
import org.example.rent.services.mappers.PhotoMapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;
import org.example.rent.entity.property.Property;
import org.example.rent.dto.propertydto.PropertyDTO;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        AddressMapper.class,
        PhotoMapper.class,
        OrderMapper.class,
        ApartmentMapper.class,
        CommercialMapper.class,
        HouseMapper.class,
        PlotsMapper.class,
        BuildingMapper.class
})
public interface PropertyMapper {

    //DTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "addressDTO", ignore = true)
    @Mapping(target = "ordersDTO", ignore = true)
    @Mapping(target = "photosDTO", ignore = true)
    @Mapping(target = "buildingDTO", ignore = true)
    Property toEntity(PropertyDTO dto);

    default Property toEntityWithRelations(PropertyDTO dto) {
        Property entity = mapToEntity(dto);
        //List<OrderDTO> -> List<Order>
        if (!dto.getOrdersDTO().isEmpty()) {
            List<Order> orders = dto.getOrdersDTO().stream()
                    .map(orderMapper()::toEntity)
                    .toList();
            entity.setOrders(orders);
        }
        //AddressDTO -> Address
        if (dto.getAddressDTO() != null) {
            Address address = addressMapper().toEntityWithRelations(dto.getAddressDTO());
            entity.setAddress(address);
        }
        //List<PhotoDTO> -> List<Photo>
        if (!dto.getPhotosDTO().isEmpty()) {
            List<Photo> photos = dto.getPhotosDTO().stream()
                    .map(photoMapper()::toEntityWithRelations)
                    .toList();
            entity.setPhotos(photos);
        }
        //BuildingDTO -> Building
        if (dto.getBuildingDTO() != null) {
            Building building = buildingMapper().toEntityWithRelations(dto.getBuildingDTO());
            entity.setBuilding(building);
        }
        return entity;
    }
    //find type of property
    default Property mapToEntity(PropertyDTO dto) {
        if (dto instanceof ApartmentDTO apartmentDTO) {
            return apartmentMapper().toEntityWithRelations(apartmentDTO);
        }else if (dto instanceof CommercialDTO commercialDTO) {
            return commercialMapper().toEntityWithRelations(commercialDTO);
        } else if (dto instanceof HouseDTO houseDTO) {
            return houseMapper().toEntityWithRelations(houseDTO);
        } else if (dto instanceof PlotsDTO plotsDTO) {
            return plotsMapper().toEntityWithRelations(plotsDTO);
        } else {
            return toEntity(dto);
        }
    }


    //Entity -> DTO
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "photos", ignore = true)
    @Mapping(target = "building", ignore = true)
    PropertyDTO toDto(Property entity);

    default PropertyDTO toDTOWithRelations(Property entity) {
        PropertyDTO dto = mapToDTO(entity);

        //List<Order> -> List<OrderDTO>
        if (!entity.getOrders().isEmpty()) {
            List<OrderDTO> orders = entity.getOrders().stream()
                    .map(orderMapper()::toDto)
                    .toList();
            dto.setOrdersDTO(orders);
        }
        //Address -> AddressDTO
        if (entity.getAddress() != null) {
            AddressDTO address = addressMapper().toDtoWithRelations(entity.getAddress());
            dto.setAddressDTO(address);
        }
        //List<Photo> -> List<PhotoDTO>
        if (!entity.getPhotos().isEmpty()) {
            List<PhotoDTO> photos = entity.getPhotos().stream()
                    .map(photoMapper()::toDtoWithRelations)
                    .toList();
            dto.setPhotosDTO(photos);
        }
        //Building -> BuildingDTO
        if (entity.getBuilding() != null) {
            BuildingDTO building =  buildingMapper().toDtoWithRelations(entity.getBuilding());
            dto.setBuildingDTO(building);
        }
        return dto;
    }

    default PropertyDTO mapToDTO(Property entity) {
        if (entity instanceof Apartment apartment) {
            return apartmentMapper().toDTOWithRelations(apartment);
        }else if (entity instanceof Commercial commercial) {
            return commercialMapper().toDTOWithRelations(commercial);
        } else if (entity instanceof House house) {
            return houseMapper().toDtoWithRelations(house);
        } else if (entity instanceof Plots plots) {
            return plotsMapper().toDtoWithRelations(plots);
        } else {
            return toDto(entity);
        }
    }

    //Getter mappers
    AddressMapper addressMapper();
    OrderMapper orderMapper();
    PhotoMapper photoMapper();
    BuildingMapper buildingMapper();
    ApartmentMapper apartmentMapper();
    CommercialMapper commercialMapper();
    HouseMapper houseMapper();
    PlotsMapper plotsMapper();
}
