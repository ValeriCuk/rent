package org.example.rent.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ApartmentDTO extends PropertyDTO {

    @NotNull
    private int bedrooms;
    @NotNull
    private int floor;
    private List<PhotoDTO> photos;
    private BuildingDTO building;
    private List<OrderDTO> order;
}
