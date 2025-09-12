package org.example.rent.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Commercial extends Property {

    private int rooms;
    private int floor;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "property",
            cascade = CascadeType.REMOVE)
    private List<Photo> photos;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "property")
    private List<Order> order;
}
