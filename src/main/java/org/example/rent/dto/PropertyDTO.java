package org.example.rent.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class PropertyDTO {

    @EqualsAndHashCode.Include
    private Long id;
    private String title;
    @NotNull
    private BigDecimal area;
    @NotNull
    private String address;
    @NotNull
    private BigDecimal pricePerSquareMeter;
    @NotNull
    private BigDecimal totalPrice;
    @NotNull
    private Date date;

}
