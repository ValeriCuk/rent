package org.example.rent.entity;

import jakarta.persistence.*;
import lombok.*;

import javax.management.relation.Role;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @EqualsAndHashCode.Include
    private String username;
    private String password;
    @EqualsAndHashCode.Include
    private String email;
    private String phone;
    private Role role;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "user",
            cascade = CascadeType.REMOVE)
    private List<Order> orders;
}
