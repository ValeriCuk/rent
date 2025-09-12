package org.example.rent.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PhotoDTO {

    @EqualsAndHashCode.Include
    private Long id;
    @NotNull
    private String fileName;
    @NotNull
    private String format;
    private long size;
    private PropertyDTO property;
    private BuildingDTO building;
    private ServicesDTO services;
}
