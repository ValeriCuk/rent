package org.example.rent.entity.property;

import jakarta.persistence.*;
import lombok.*;
import org.example.rent.entity.Building;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Apartment extends Property {

    private int bedrooms;
    private int floor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id")
    private Building building;
}
