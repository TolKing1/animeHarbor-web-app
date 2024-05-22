package org.tolking.animeharbor.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import org.tolking.animeharbor.entities.enums.AnimeStatus;
import org.tolking.animeharbor.entities.enums.AnimeType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(schema = "public")
public class Anime extends TransactionEntity{

    @NotBlank(message = "Title can't be blank")
    private String title;

    @Column(columnDefinition = "text")
    @NotBlank(message = "Description can't be blank")
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Type can't be null")
    private AnimeType type;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @NotBlank(message = "Director name can't be blank")
    private String director;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status can't be null")
    private AnimeStatus status;


    @OneToMany(mappedBy = "anime", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Views> views = new ArrayList<>();


    @ManyToMany(mappedBy = "animeList", fetch = FetchType.LAZY)
    private List<Genre> genre = new ArrayList<>();

    @OneToMany(mappedBy = "anime", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Rating> ratings = new ArrayList<>();

    @OneToMany(mappedBy = "anime", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();


    @ManyToOne
    @JoinColumn
    private Studio studio;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @NotNull
    private Image image;

    @Transient
    public long getViewCount() {
        return views.size();
    }

    @Transient
    public long getRatingCount() {
        return ratings.size();
    }

    @Transient
    public double getAverageRating() {
        return ratings.stream().mapToDouble(Rating::getScore).average().orElse(0.0);
    }

    @PreRemove
    private void removeGenreAssociations() {
        for (Genre genre: this.genre) {
            genre.getAnimeList().remove(this);
        }
    }

    @PrePersist
    private void addAssociations() {
        studio.getAnimeList().add(this);

        for (Genre genre: this.genre) {
            genre.getAnimeList().add(this);
        }
    }

    @PreUpdate
    private void updateAssociations() {
        studio.getAnimeList().add(this);

        for (Genre genre: this.genre) {
            genre.getAnimeList().add(this);
        }
    }

}
