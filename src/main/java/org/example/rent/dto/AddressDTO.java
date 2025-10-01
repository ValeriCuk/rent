package org.example.rent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AddressDTO {

    @EqualsAndHashCode.Include
    private Long id;
    private String region;
    private String street;
    private String city;
    private String district;
    private String buildingNumber;
    private Integer apartmentNumber;
}
