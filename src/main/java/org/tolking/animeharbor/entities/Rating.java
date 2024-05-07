package org.tolking.animeharbor.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn
    private Anime anime;

    @ManyToOne
    @JoinColumn
    private User user;

    @Size(min = 1,max = 10,message = "Please choose rating between 1 and 10")
    @JoinColumn
    private int score;
}
