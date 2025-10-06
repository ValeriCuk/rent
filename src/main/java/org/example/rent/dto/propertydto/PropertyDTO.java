package org.example.rent.dto.propertydto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.rent.dto.AddressDTO;
import org.example.rent.dto.BuildingDTO;
import org.example.rent.dto.PhotoDTO;
import org.example.rent.other.PropertyType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuperBuilder
public abstract class PropertyDTO {

    @EqualsAndHashCode.Include
    private Long id;
    private String title;
    @NotNull
    private BigDecimal area;
    @NotNull
    private AddressDTO address;
    @NotNull
    private BigDecimal pricePerSquareMeter;
    @NotNull
    private BigDecimal totalPrice;
    @NotNull
    private LocalDateTime date;
    private BuildingDTO building;
    private Set<PhotoDTO> photos;
    private PropertyType type;

    public String getTypeDisplay(){
        return type.toString();
    }

    public String getBedroomsDisplay() {
        return "--";
    }

    public String getFloorDisplay() {
        return "--";
    }

    public String getFloorsDisplay() {
        return building == null ? "--" : String.valueOf(building.getFloors());
    }
}
