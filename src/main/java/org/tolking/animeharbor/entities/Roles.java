package org.tolking.animeharbor.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.tolking.animeharbor.entities.enums.RoleType;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(schema = "public")
public class Roles {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType role;

    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();
}
