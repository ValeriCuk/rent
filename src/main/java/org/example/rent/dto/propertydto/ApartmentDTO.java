package org.example.rent.dto.propertydto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.rent.dto.BuildingDTO;

@EqualsAndHashCode(callSuper = true)
@Data
public class ApartmentDTO extends PropertyDTO {

    @NotNull
    @Min(1)
    private int bedrooms;
    @NotNull
    @Min(1)
    private int floor;
    private BuildingDTO building;
}
