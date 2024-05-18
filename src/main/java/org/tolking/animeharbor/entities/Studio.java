package org.tolking.animeharbor.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(schema = "public")
public class Studio extends TransactionEntity{
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "studio")
    private List<Anime> animeList;
}
