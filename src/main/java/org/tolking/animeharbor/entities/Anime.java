package org.tolking.animeharbor.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.tolking.animeharbor.entities.enums.AnimeStatus;
import org.tolking.animeharbor.entities.enums.AnimeType;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Column(columnDefinition = "timestamp(6) DEFAULT now()")
    private LocalDateTime insert_date;

    @Enumerated(EnumType.STRING)
    private AnimeStatus status;

    @OneToOne(mappedBy = "anime")
    private Views views;

    @ManyToMany(mappedBy = "animeList")
    private List<Genre> genre;

    @ManyToOne
    @JoinColumn
    private Studio studio;

}
