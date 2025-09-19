package org.example.rent.dto.propertydto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.rent.dto.AddressDTO;
import org.example.rent.dto.BuildingDTO;
import org.example.rent.dto.PhotoDTO;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class PropertyDTO {

    @EqualsAndHashCode.Include
    private Long id;
    private String title;
    @NotNull
    private BigDecimal area;
    @NotNull
    private AddressDTO addressDTO;
    @NotNull
    private BigDecimal pricePerSquareMeter;
    @NotNull
    private BigDecimal totalPrice;
    @NotNull
    private Date date;
    private BuildingDTO buildingDTO;
    private List<PhotoDTO> photosDTO;
}
