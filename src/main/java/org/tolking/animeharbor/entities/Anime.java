package org.tolking.animeharbor.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.tolking.animeharbor.entities.enums.AnimeStatus;
import org.tolking.animeharbor.entities.enums.AnimeType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
@Table(schema = "public")
public class Anime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Title can't be blank")
    private String title;

    @Column(columnDefinition = "text")
    @NotBlank(message = "Description can't be blank")
    private String description;

    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Type can't be blank")
    private AnimeType type;

    @NotBlank(message = "Date can't be blank")
    private LocalDate date;

    @NotBlank(message = "Director name can't be blank")
    private String director;

    @CreationTimestamp
    private LocalDateTime creation;

    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Status can't be blank")
    private AnimeStatus status;

    @OneToMany(mappedBy = "anime", fetch = FetchType.LAZY)
    private List<Views> views = new ArrayList<>();

    @ManyToMany(mappedBy = "animeList")
    private Set<Genre> genre = new TreeSet<>(Comparator.comparing(Genre::getTitle));

    @OneToMany(mappedBy = "anime")
    private List<Rating> ratings = new ArrayList<>();

    @ManyToOne
    @JoinColumn
    private Studio studio;

    @NotBlank(message = "Image can't be empty")
    @ManyToOne
    private Image image;

    @Transient
    public long getViewCount() {
        return views.size();
    }

    @Transient
    public double getAverageRating() {
        return ratings.stream().mapToDouble(Rating::getScore).average().orElse(0.0);
    }
}
