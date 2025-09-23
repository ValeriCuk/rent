package org.example.rent.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.rent.dto.propertydto.PropertyDTO;

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
    private String url;
}
