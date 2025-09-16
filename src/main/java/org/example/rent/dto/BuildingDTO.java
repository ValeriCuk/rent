package org.example.rent.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.rent.dto.propertydto.ApartmentDTO;

import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BuildingDTO {

    @EqualsAndHashCode.Include
    private Long id;
    private String buildingName;
    @NotNull
    @Min(3)
    private int floors;
    private int insideArea;
    private int outsideArea;
    @NotNull
    private int year;
    @EqualsAndHashCode.Include
    @NotNull
    private AddressDTO address;
    private List<ApartmentDTO> apartments;
    private List<PhotoDTO> photos;
}
