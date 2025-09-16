package org.example.rent.entity.property;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Commercial extends Property {

    private int rooms;
    private int floor;
}
