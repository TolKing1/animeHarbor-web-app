package org.tolking.animeharbor.repositories;

import org.springframework.data.repository.CrudRepository;
import org.tolking.animeharbor.entities.Roles;
import org.tolking.animeharbor.entities.enums.RoleType;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Roles, Integer> {
    Optional<Roles> findByRole(RoleType role);
}
