package org.example.rent.entity.property;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class House extends Property {

    private int bedrooms;
    private int floors;
    private BigDecimal outsideArea;

}
