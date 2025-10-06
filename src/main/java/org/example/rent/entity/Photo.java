package org.example.rent.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.rent.other.PhotoType;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;

    private String fileName;
    private String format;
    private long size;
    private String url;
    private PhotoType type;
}
