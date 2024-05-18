package org.tolking.animeharbor.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"anime_id", "user_id"}), schema = "public")
public class Rating extends TransactionEntity{

    @ManyToOne
    @JoinColumn
    private Anime anime;

    @ManyToOne
    @JoinColumn
    private User user;

    @Range(min = 0,max = 10,message = "Please choose rating between 0 and 10")
    @JoinColumn
    private int score;

}
