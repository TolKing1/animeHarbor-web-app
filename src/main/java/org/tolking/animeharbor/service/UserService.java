package org.tolking.animeharbor.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.tolking.animeharbor.dto.RegisterDto;
import org.tolking.animeharbor.entities.User;

import javax.management.relation.RoleNotFoundException;

public interface UserService extends UserDetailsService {
    User findByUsername(String username);
    User findByEmail(String email);
    User findById(long id);
    User save(RegisterDto registerDto) throws RoleNotFoundException;

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
