package org.tolking.animeharbor.dto.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;
import org.tolking.animeharbor.dto.DTOConverter;
import org.tolking.animeharbor.entities.Image;
import org.tolking.animeharbor.entities.User;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class UserDetailDTO extends DTOConverter<User, UserDetailDTO> {
    private String username;
    private String email;
    private Image image;

    @Override
    protected Class<User> getTypeEntity() {
        return User.class;
    }

    @Override
    protected Class<UserDetailDTO> getTypeDTO() {
        return UserDetailDTO.class;
    }
}
