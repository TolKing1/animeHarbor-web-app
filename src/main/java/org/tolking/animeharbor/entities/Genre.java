package org.tolking.animeharbor.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(schema = "public")
public class Genre extends TransactionEntity{

    @UniqueElements(message = "Title must be unique")
    private String title;

    @NotBlank(message = "Description can not be blank")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "genre_m2m_anime",
            joinColumns = @JoinColumn(name = "genre_id"),
            inverseJoinColumns = @JoinColumn(name = "anime_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"genre_id", "anime_id"})
    )
    private List<Anime> animeList = new ArrayList<>();

    @Transient
    public boolean isEmpty(){
        return animeList.isEmpty();
    }
}
