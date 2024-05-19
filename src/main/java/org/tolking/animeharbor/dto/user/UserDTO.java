package org.tolking.animeharbor.dto.user;

import lombok.Data;
import org.tolking.animeharbor.dto.AnimeDTO;
import org.tolking.animeharbor.dto.RolesDTO;
import org.tolking.animeharbor.entities.Image;
import org.tolking.animeharbor.entities.enums.Provider;

import java.util.List;

@Data
public class UserDTO {
    private String username;
    private String email;
    private String password;
    private Provider provider;
    private boolean enabled;
    private Image image;
    private List<RolesDTO> roles;
    private List<AnimeDTO> watchList;
}
