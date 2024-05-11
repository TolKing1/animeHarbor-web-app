package org.tolking.animeharbor.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @UniqueElements(message = "Title must be unique")
    private String title;

    @NotBlank(message = "Description can not be blank")
    private String description;
    @ManyToMany
    @JoinTable(
            name = "genre_m2m_anime",
            joinColumns = @JoinColumn(name = "genre_id"),
            inverseJoinColumns = @JoinColumn(name = "anime_id")
    )
    private List<Anime> animeList = new ArrayList<Anime>();

    @Transient
    public boolean isEmpty(){
        return animeList.isEmpty();
    }
}
