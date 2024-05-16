package org.tolking.animeharbor.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.tolking.animeharbor.entities.enums.ImageType;

import java.time.LocalDateTime;

@Entity
@Data
public class Image{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String filename;

    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    @Column(columnDefinition = "text")
    private String data;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
