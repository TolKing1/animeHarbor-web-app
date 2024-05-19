package org.tolking.animeharbor.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(schema = "public")
public class Studio extends TransactionEntity{

    @Size(min = 3, max = 30, message = "Length should be between 3 and 30")
    @Column(unique= true)
    private String name;

    @Column(columnDefinition = "text")
    @Size(min = 3, max = 400, message = "Length should be between 3 and 400")
    private String description;

    @OneToMany(mappedBy = "studio")
    private List<Anime> animeList = new ArrayList<>();

    @Transient
    public boolean isEmptyAnimeList(){
        return animeList.isEmpty();
    }
}
