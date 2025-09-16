package org.example.rent.dto.propertydto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class HouseDTO extends PropertyDTO {

    @NotNull
    private int bedrooms;
    @NotNull
    private int floors;
    @NotNull
    private BigDecimal outsideArea;
}
