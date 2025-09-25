package org.example.rent.dto.propertydto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CommercialDTO extends PropertyDTO {

    private int bedrooms;
    @NotNull
    private int floor;
}
