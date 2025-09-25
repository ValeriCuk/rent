package org.example.rent.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.rent.other.ServicesStatus;

import java.sql.Date;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ServicesDTO {

    @EqualsAndHashCode.Include
    private Long id;
    private String title;
    private String description;
    @NotNull
    private Date date;
    private List<PhotoDTO> photos;
    private ServicesStatus status;
}
