package org.example.rent.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CommercialDTO extends PropertyDTO {
    private int rooms;
    @NotNull
    private int floor;
    private List<PhotoDTO> photos;
    private List<OrderDTO> order;
}
