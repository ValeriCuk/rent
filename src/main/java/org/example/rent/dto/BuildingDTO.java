package org.example.rent.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.rent.entity.Photo;
import org.example.rent.other.BuildingStatus;

import java.util.List;
import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BuildingDTO {

    @EqualsAndHashCode.Include
    private Long id;
    private String buildingName;
    private BuildingStatus status;
    private LocationDTO location;
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
    private Set<Photo> photos;
}
