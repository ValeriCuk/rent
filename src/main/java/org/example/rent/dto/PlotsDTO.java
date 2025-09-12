package org.example.rent.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
public class PlotsDTO extends PropertyDTO {

    private List<PhotoDTO> photos;
    private List<OrderDTO> orders;

}
