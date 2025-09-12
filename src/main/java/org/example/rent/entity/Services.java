package org.example.rent.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String title;
    private String description;
    private Date date;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "services",
            cascade = CascadeType.REMOVE)
    private List<Photo> photos;
}
