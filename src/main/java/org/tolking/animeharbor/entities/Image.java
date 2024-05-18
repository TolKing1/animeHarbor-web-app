package org.tolking.animeharbor.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.tolking.animeharbor.entities.enums.ImageType;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(schema = "public")
public class Image extends TransactionEntity{

    private String filename;

    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    @Column(columnDefinition = "text")
    private String data;


}
