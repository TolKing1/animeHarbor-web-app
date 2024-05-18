package org.tolking.animeharbor.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(schema = "public")
public class Comment extends BaseEntity {

    @Size(min = 3, max = 200, message = "Write something between 3 and 200")
    private String comment;

    @ManyToOne
    private User user;

    @ManyToOne
    private Anime anime;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
