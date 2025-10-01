package org.example.rent.dto.propertydto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.rent.other.PropertyType;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class PlotsDTO extends PropertyDTO {

    @Override
    public String getTypeDisplay() {
        return  PropertyType.PLOT.toString();
    }
}
