package org.example.rent.entity.property;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Apartment extends Property {

    private int bedrooms;
    private int floor;
}
