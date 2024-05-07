package org.tolking.animeharbor.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity()
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String email;

    private String password;

    private String roles;

    @ManyToMany
    @JoinTable(
            name = "watchlist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "anime_id")
    )
    private List<Anime> watchList;

    @OneToMany(mappedBy = "user")
    private List<Rating> rating;
}
