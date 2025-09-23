package org.example.rent.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.rent.entity.Photo;
import org.example.rent.other.BuildingStatus;

import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BuildingDTO {

    @EqualsAndHashCode.Include
    private Long id;
    private String buildingName;
    private BuildingStatus status;
    private LocationDTO locationDTO;
    @NotNull
    @Min(3)
    private int floors;
    private int insideArea;
    private int outsideArea;
    @NotNull
    private int year;
    @EqualsAndHashCode.Include
    @NotNull
    private AddressDTO addressDTO;
    private List<Photo> photos;
}
