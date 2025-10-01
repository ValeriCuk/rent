package org.example.rent.dto.propertydto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.rent.other.PropertyType;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CommercialDTO extends PropertyDTO {

    @NotNull
    private int floor;

    @Override
    public String getTypeDisplay() {
        return  PropertyType.COMMERCIAL.toString();
    }

    @Override
    public String getFloorDisplay() {
        return String.valueOf(floor);
    }
}
