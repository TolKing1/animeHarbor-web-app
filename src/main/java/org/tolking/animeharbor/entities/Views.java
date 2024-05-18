package org.tolking.animeharbor.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(schema = "public")
public class Views extends BaseEntity {
    @ManyToOne
    @JoinColumn
    private Anime anime;

    @CreationTimestamp
    private LocalDateTime viewDate;
}
