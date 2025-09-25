package org.example.rent.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.rent.dto.propertydto.PropertyDTO;
import org.example.rent.other.ViewingRequestStatus;

import java.sql.Date;

@Data
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ViewingRequestDTO {

    @EqualsAndHashCode.Include
    private Long id;
    private String comments;
    private ViewingRequestStatus status;
    private Date date;
    @NotNull
    private UserDTO user;
    @NotNull
    private PropertyDTO property;
}
