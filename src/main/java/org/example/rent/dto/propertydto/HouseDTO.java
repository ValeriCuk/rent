package org.example.rent.dto.propertydto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.rent.dto.OrderDTO;
import org.example.rent.dto.PhotoDTO;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class HouseDTO extends PropertyDTO {

    @NotNull
    private int rooms;
    @NotNull
    private int floor;
}
