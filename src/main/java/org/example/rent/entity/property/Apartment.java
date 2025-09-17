package org.example.rent.entity.property;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Apartment extends Property {

    private int bedrooms;
    private int floor;
}
