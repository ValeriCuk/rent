package org.example.rent.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.rent.dto.propertydto.PropertyDTO;
import org.example.rent.entity.Building;
import org.example.rent.entity.property.Property;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AddressDTO {

    @EqualsAndHashCode.Include
    private Long id;
    private String region;
    private String street;
    private String city;
    private String district;
    private String buildingNumber;
    private Integer apartmentNumber;
    private PropertyDTO property;
    private BuildingDTO building;
}
