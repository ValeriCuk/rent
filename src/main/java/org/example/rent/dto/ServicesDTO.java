package org.example.rent.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.rent.other.ServicesStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ServicesDTO {

    @EqualsAndHashCode.Include
    private Long id;
    private String title;
    private String description;
    @NotNull
    private LocalDateTime date;
    private List<PhotoDTO> photos;
    private ServicesStatus status;
}
