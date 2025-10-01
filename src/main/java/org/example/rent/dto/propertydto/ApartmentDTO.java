package org.example.rent.dto.propertydto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.rent.other.PropertyType;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
public class ApartmentDTO extends PropertyDTO {

    @NotNull
    @Min(1)
    private int bedrooms;
    @NotNull
    @Min(1)
    private int floor;

    @Override
    public String getTypeDisplay() {
        return  PropertyType.APARTMENT.toString();
    }

    @Override
    public String getBedroomsDisplay() {
        return String.valueOf(bedrooms);
    }

    @Override
    public String getFloorDisplay() {
        return String.valueOf(floor);
    }
}
