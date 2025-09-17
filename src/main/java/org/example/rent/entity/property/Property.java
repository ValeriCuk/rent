package org.example.rent.entity.property;

import jakarta.persistence.*;
import lombok.*;
import org.example.rent.entity.Address;
import org.example.rent.entity.Building;
import org.example.rent.entity.Order;
import org.example.rent.entity.Photo;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String title;
    private BigDecimal area;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    private BigDecimal pricePerSquareMeter;
    private BigDecimal totalPrice;
    private Date date;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "property",
            cascade = CascadeType.REMOVE)
    private List<Order> orders;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "property",
            cascade = CascadeType.REMOVE)
    private List<Photo> photos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id")
    private Building building;
}
