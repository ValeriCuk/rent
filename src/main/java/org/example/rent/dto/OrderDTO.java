package org.example.rent.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderDTO {

    @EqualsAndHashCode.Include
    private Long id;
    private String comments;
    private boolean status;
    @NotNull
    private UserDTO user;
    @NotNull
    private PropertyDTO property;

}
