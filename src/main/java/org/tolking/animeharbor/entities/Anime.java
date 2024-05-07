package org.tolking.animeharbor.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.tolking.animeharbor.entities.enums.AnimeStatus;
import org.tolking.animeharbor.entities.enums.AnimeType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Anime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @Column(columnDefinition = "text")
    private String description;

    @Enumerated(EnumType.STRING)
    private AnimeType type;

    private LocalDate date;

    private String director;

    @CreationTimestamp
    private LocalDateTime creation;

    @Enumerated(EnumType.STRING)
    private AnimeStatus status;

    @OneToMany(mappedBy = "anime")
    private List<Views> views = new ArrayList<>();

    @ManyToMany(mappedBy = "animeList")
    private List<Genre> genre = new ArrayList<>();

    @OneToMany(mappedBy = "anime")
    private List<Rating> ratings = new ArrayList<>();

    @ManyToOne
    @JoinColumn
    private Studio studio;

}
