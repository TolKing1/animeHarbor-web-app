package org.tolking.animeharbor.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Views {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn
    private Anime anime;

    private long view_count;
}
