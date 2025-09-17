package org.example.rent.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.rent.dto.propertydto.PropertyDTO;

import java.sql.Date;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderDTO {

    @EqualsAndHashCode.Include
    private Long id;
    private String comments;
    private boolean status;
    private Date date;
    @NotNull
    private UserDTO user;
    @NotNull
    private PropertyDTO property;

}
