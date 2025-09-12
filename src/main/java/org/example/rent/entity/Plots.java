package org.example.rent.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Plots extends Property{

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "property",
            cascade = CascadeType.REMOVE)
    private List<Photo> photos;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "property",
            cascade = CascadeType.REMOVE)
    private List<Order> orders;
}
