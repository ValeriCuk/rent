package org.example.rent.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Apartment extends Property {

    private int bedrooms;
    private int floor;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "property",
            cascade = CascadeType.REMOVE)
    private List<Photo> photos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id")
    private Building building;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "property",
            cascade = CascadeType.REMOVE)
    private List<Order> order;

}
