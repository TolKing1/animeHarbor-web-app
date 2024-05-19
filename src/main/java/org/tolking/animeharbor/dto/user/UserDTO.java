package org.tolking.animeharbor.dto.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;
import org.tolking.animeharbor.dto.DTOConverter;
import org.tolking.animeharbor.dto.RolesDTO;
import org.tolking.animeharbor.entities.User;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class UserDTO extends DTOConverter<User,UserDTO> {
    private int id;
    private String username;
    private String email;
    private String provider;
    private boolean enabled;
    private List<RolesDTO> roles;
    private LocalDateTime created;
    private LocalDateTime updated;

    public boolean hasRole(String strRole) {
        return roles.stream()
                .map(RolesDTO::getRole)
                .anyMatch(role -> role.equalsIgnoreCase(strRole));
    }

    @Override
    protected Class<User> getTypeEntity() {
        return User.class;
    }

    @Override
    protected Class<UserDTO> getTypeDTO() {
        return UserDTO.class;
    }
}
