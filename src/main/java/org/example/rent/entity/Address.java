package org.example.rent.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.rent.entity.property.Property;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;
    private String region;
    private String street;
    private String city;
    private String district;
    private String buildingNumber;
    private Integer apartmentNumber;


    public Address (String region, String street, String city, String district, String buildingNumber, Integer apartmentNumber) {
        this.region = region;
        this.street = street;
        this.city = city;
        this.district = district;
        this.buildingNumber = buildingNumber;
        this.apartmentNumber = apartmentNumber;
    }

    public Address (String region, String street, String city, String district, String buildingNumber) {
        this.region = region;
        this.street = street;
        this.city = city;
        this.district = district;
        this.buildingNumber = buildingNumber;
    }
}
