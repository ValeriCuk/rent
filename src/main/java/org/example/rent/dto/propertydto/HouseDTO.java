package org.example.rent.dto.propertydto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.rent.other.PropertyType;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class HouseDTO extends PropertyDTO {

    @NotNull
    private int bedrooms;
    @NotNull
    private int floors;
    @NotNull
    private BigDecimal outsideArea;

    @Override
    public String getTypeDisplay() {
        return  PropertyType.HOUSE.toString();
    }

    @Override
    public String getBedroomsDisplay() {
        return String.valueOf(bedrooms);
    }

    @Override
    public String getFloorsDisplay() {
        return String.valueOf(floors);
    }
}
