package org.example.rent.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.rent.other.ServicesStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private LocalDateTime date;
    private ServicesStatus status;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    @JoinColumn(name = "services_id")
    private List<Photo> photos = new ArrayList<>();
}
