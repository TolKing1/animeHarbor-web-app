package org.tolking.animeharbor.dto;

import lombok.Data;
import org.tolking.animeharbor.entities.enums.RoleType;

@Data
public class RolesDTO {
    private long id;
    private RoleType role;
}
