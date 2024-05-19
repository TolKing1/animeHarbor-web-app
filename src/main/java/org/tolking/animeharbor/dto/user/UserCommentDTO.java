package org.tolking.animeharbor.dto.user;

import lombok.Data;
import org.tolking.animeharbor.dto.ImageDTO;

@Data
public class UserCommentDTO {
    private String username;
    private ImageDTO image;
}
