package org.example.rent.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @OneToOne(mappedBy = "address")
    private Property property;

    @OneToOne(mappedBy = "address")
    private Building building;

}
